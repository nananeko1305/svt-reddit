package com.ftn.redditClone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.redditClone.elastic.dto.CommunityElasticDTO;
import com.ftn.redditClone.elastic.dto.MultipleValuesDTO;
import com.ftn.redditClone.elastic.dto.MultipleValuesPostDTO;
import com.ftn.redditClone.elastic.dto.PostElasticDTO;
import com.ftn.redditClone.elastic.model.CommunityElastic;
import com.ftn.redditClone.elastic.model.PostElastic;
import com.ftn.redditClone.elastic.service.CommunityElasticService;
import com.ftn.redditClone.elastic.service.PostElasticService;
import com.ftn.redditClone.model.dto.*;
import com.ftn.redditClone.model.entity.*;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.*;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private DTOService dtoService;

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private FlairService flairService;
    @Autowired
    private CommentService commentService;

    @Autowired
    public PostElasticService postElasticService;

    @Autowired
    public CommunityElasticService communityElasticService;

    @Autowired
    public ObjectMapper objectMapper;

    @GetMapping(value = "{id}")
    public ResponseEntity<PostDTO> getOne(@PathVariable int id){

        Post post = postService.findById(id);
        return new ResponseEntity<>(new PostDTO(post), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/reactions")
    private ResponseEntity<List<ReactionDTO>> reactionsForPost(@PathVariable int id) {
        return new ResponseEntity<>(dtoService.reactionToDTO(postService.findById(id).getReactions()), HttpStatus.OK);
    }

    @GetMapping("{id}/comments")
    private ResponseEntity<List<CommentDTO>> commentsForPost(@PathVariable int id){
        List<Comment> comments = postService.findById(id).getComments();
        List<Comment> returnComments = new ArrayList<>();
        for (Comment comment: comments){
            if (!comment.isDeleted())
                returnComments.add(comment);
        }
        return new ResponseEntity<>(dtoService.commentToDTO(returnComments), HttpStatus.OK);
    }

    @GetMapping("sort/{sortType}")
    private ResponseEntity<List<PostDTO>> sortedPosts(@PathVariable String sortType){
        List<Post> posts = new ArrayList<>();
        if(sortType.equals("Top")){
            posts = postService.sortedList("Top");
        }else if(sortType.equals("Hot")){
            posts = postService.sortedList("Hot");
        }
        return new ResponseEntity<>(dtoService.postToDTO(posts), HttpStatus.OK);
    }

    @GetMapping("{id}/comments/sort/{sortType}")
    private ResponseEntity<List<CommentDTO>> sortedCommentForPost(@PathVariable int id, @PathVariable String sortType){
        List<Comment> comments = new ArrayList<>();
        if(sortType.equals("Top")){
            comments = commentService.soredList("Top", id);
        }else if(sortType.equals("New")){
            comments = commentService.soredList("New", id);
        }else if(sortType.equals("Old")){
            comments = commentService.soredList("Old", id);
        }
        return new ResponseEntity<>(dtoService.commentToDTO(comments), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PostDTO>> findAll(){

        List<Post> posts = postService.findAll();
        List<Post> returnPosts = new ArrayList<>();

        for (Post post : posts) {
            if(!post.isDeleted())
                returnPosts.add(post);
        }
        List<PostDTO> returnPostsDTO = dtoService.postToDTO(returnPosts);
//        Collections.shuffle(returnPosts,new Random());
        return new ResponseEntity<>(returnPostsDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDTO> createPost(@RequestParam("pdfFile") MultipartFile pdfFile,
                                              @RequestParam("jsonFile") String jsonFile,
                                              @RequestHeader("Authorization") String bearer){
        Post post = new Post();
        String pdfSTR = "";

        try {
            post = objectMapper.readValue(jsonFile.getBytes(), Post.class);
            if(pdfFile != null){
                if(!pdfFile.isEmpty()){
                    pdfSTR = postElasticService.indexUploadedFile(pdfFile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String token = bearer.substring(7);
        String username = tokenUtils.getUsernameFromToken(token);

        Community community = communityService.findById(post.getCommunity().getId());
        User user = userService.findByUsername(username);
        post.setUser(user);
        post.setCommunity(community);
        if(post.getFlair().getId() != 0){
            post.setFlair(flairService.findOne(post.getFlair().getId()).get());
        }else {
            post.setFlair(null);
        }
        for (Banned banned : community.getBanneds()) {
            if (banned.getUser().getId() == user.getId()) {
                return new ResponseEntity<>(new PostDTO(), HttpStatus.OK);

            }
        }
        Post returnPost = postService.save(post);

        postElasticService.savePost(returnPost, pdfSTR);
        CommunityElastic communityElastic = communityElasticService.findById(returnPost.getCommunity().getId());
        communityElastic.setNumberOfPosts(communityElastic.getNumberOfPosts() + 1);
        communityElasticService.index(communityElastic);

        return new ResponseEntity<>(new PostDTO(returnPost), HttpStatus.CREATED);
    }

    @PostMapping(value = "{id}/reactions")
    public ResponseEntity<ReactionDTO> UpvoteDownvote(@RequestHeader("Authorization") String bearer, @RequestBody ReactionDTO reactionDTO, @PathVariable int id) {

        Post post = postService.findById(id);
        Community community = communityService.findById(post.getCommunity().getId());
        String token = bearer.substring(7);
        String username = tokenUtils.getUsernameFromToken(token);
        Reaction reaction = new Reaction(reactionDTO);
        User user = userService.findByUsername(username);
        reaction.setUser(user);
        reaction.setComment(null);
        reaction.setPost(post);
        for (Banned banned : community.getBanneds()) {
            if (banned.getUser().getId() == user.getId()) {
                return new ResponseEntity<>(new ReactionDTO(), HttpStatus.OK);

            }
        }
        if (reactionService.alreadyVoted(user.getId(), post.getId()).isEmpty()) {
            reactionService.saveReaction(reaction);
        }
        return new ResponseEntity<>(new ReactionDTO(reaction), HttpStatus.OK);
    }



    @PutMapping(consumes = "application/json")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO) {

        Post post = postService.findById(postDTO.getId());

        if (postDTO.getTitle() != null) {
            post.setTitle(postDTO.getTitle());
        }

        if (postDTO.getText() != null) {
            post.setText(postDTO.getText());
        }

        if (postDTO.getImagePath() != null) {
            post.setImagePath(postDTO.getImagePath());
        }

        postService.save(post);

        return new ResponseEntity<>(new PostDTO(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PostDTO> deletePost(@PathVariable int id){

        Post post = postService.findById(id);
        post.setDeleted(true);
        postService.save(post);

        return new ResponseEntity<>(new PostDTO(post), HttpStatus.OK);
    }

    public String convertMultipartFileToString(MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @GetMapping("findPostsByMultipleValues")
    public ResponseEntity<List<PostElasticDTO>> findCommunitiesByMultipleValues(@RequestBody MultipleValuesPostDTO multipleValuesPostDTO){
        return new ResponseEntity<>(postElasticService.findAllByMultipleValues(multipleValuesPostDTO), HttpStatus.OK);
    }

    @GetMapping("findPostsByComment")
    public ResponseEntity<List<PostElasticDTO>> findCommunitiesByCommentText(@RequestBody MultipleValuesPostDTO multipleValuesPostDTO){
        return new ResponseEntity<>(postElasticService.searchByCommentText(multipleValuesPostDTO), HttpStatus.OK);
    }

    @GetMapping("findPostsByFlair")
    public ResponseEntity<List<PostElasticDTO>> findCommunitiesByFlair(@RequestBody MultipleValuesPostDTO multipleValuesPostDTO){
        return new ResponseEntity<>(postElasticService.searchByFlair(multipleValuesPostDTO), HttpStatus.OK);
    }

}
