package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.CommentDTO;
import com.ftn.redditClone.model.dto.FlairDTO;
import com.ftn.redditClone.model.dto.PostDTO;
import com.ftn.redditClone.model.dto.ReactionDTO;
import com.ftn.redditClone.model.entity.*;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(consumes = "application/json")
    public ResponseEntity<PostDTO> getOne(@RequestBody PostDTO postDTO){

        Post post = postService.findById(postDTO.getId());
        postService.save(post);

        return new ResponseEntity<>(new PostDTO(), HttpStatus.FOUND);
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

    @GetMapping()
    public ResponseEntity<List<PostDTO>> findAll(){

        List<Post> posts = postService.findAll();
        List<Post> returnPosts = new ArrayList<>();

        for (Post post : posts) {
            if(!post.getCommunity().isSuspended)
                returnPosts.add(post);
        }
        List<PostDTO> returnPostsDTO = dtoService.postToDTO(returnPosts);
//        Collections.shuffle(returnPosts,new Random());
        return new ResponseEntity<>(returnPostsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "{id}/reactions")
    public ResponseEntity<ReactionDTO> UpvoteDownvote(@RequestHeader("Authorization") String bearer, @RequestBody ReactionDTO reactionDTO, @PathVariable int id){

        String token = bearer.substring(7);
        String username = tokenUtils.getUsernameFromToken(token);
        Reaction reaction = new Reaction(reactionDTO);
        User user = userService.findByUsername(username);
        reaction.setUser(user);
        reaction.setComment(null);
        Post post = postService.findById(id);
        reaction.setPost(post);
        if(reactionService.alreadyVoted(user.getId(), post.getId()).isEmpty()){
            reactionService.saveReaction(reaction);
        }
        return new ResponseEntity<>(new ReactionDTO(reaction), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @RequestHeader("Authorization") String bearer){

        String token = bearer.substring(7);
        String username = tokenUtils.getUsernameFromToken(token);
        Community community = communityService.findById(postDTO.getCommunity().getId());
        User user = userService.findByUsername(username);
        Post post = new Post(postDTO);
        post.setUser(user);
        post.setCommunity(community);
        if(postDTO.getFlair().getId() != 0){
            post.setFlair(flairService.findOne(postDTO.getFlair().getId()).get());
        }else {
            post.setFlair(null);
        }
        Post returnPost = postService.save(post);
        return new ResponseEntity<>(new PostDTO(returnPost), HttpStatus.CREATED);
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
    public void deletePost(@PathVariable int id){

        Post post = postService.findById(id);
        postService.delete(post);
    }
}
