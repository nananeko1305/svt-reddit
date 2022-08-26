package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.*;
import com.ftn.redditClone.model.entity.*;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.*;
import org.hibernate.mapping.Any;
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
    @Autowired
    private CommentService commentService;

    @GetMapping(value = "{id}")
    public ResponseEntity<PostDTO> getOne(@PathVariable int id) {

        Post post = postService.findById(id);
        return new ResponseEntity<>(new PostDTO(post), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/reactions")
    private ResponseEntity<List<ReactionDTO>> reactionsForPost(@PathVariable int id) {
        return new ResponseEntity<>(dtoService.reactionToDTO(postService.findById(id).getReactions()), HttpStatus.OK);
    }

    @GetMapping("{id}/comments")
    private ResponseEntity<List<CommentDTO>> commentsForPost(@PathVariable int id) {
        List<Comment> comments = postService.findById(id).getComments();
        List<Comment> returnComments = new ArrayList<>();
        for (Comment comment : comments) {
            if (!comment.isDeleted() && comment.getParentComment() == 0)
                returnComments.add(comment);
        }
        return new ResponseEntity<>(dtoService.commentToDTO(returnComments), HttpStatus.OK);
    }

    @GetMapping("sort/{sortType}")
    private ResponseEntity<List<PostDTO>> sortedPosts(@PathVariable String sortType) {
        List<Post> posts = new ArrayList<>();
        if (sortType.equals("Top")) {
            for (Post post : postService.sortedList("Top")) {
                if (!post.isDeleted()) {
                    posts.add(post);
                }
            }
        } else if (sortType.equals("Hot")) {
            for (Post post : postService.sortedList("Hot")) {
                if (!post.isDeleted()) {
                    posts.add(post);
                }
            }
        } else if (sortType.equals("downvote")) {
            for (Post post : postService.sortedDownvote()) {
                if (!post.isDeleted()) {
                    posts.add(post);
                }
            }
        } else {
            for (Post post : postService.sortedVote()) {
                if (!post.isDeleted()) {
                    posts.add(post);
                }
            }
        }
        return new ResponseEntity<>(dtoService.postToDTO(posts), HttpStatus.OK);
    }

    @GetMapping("{id}/comments/sort/{sortType}")
    private ResponseEntity<List<CommentDTO>> sortedCommentForPost(@PathVariable int id,
                                                                  @PathVariable String sortType) {
        List<Comment> comments = new ArrayList<>();
        if (sortType.equals("Top")) {
            for (Comment comment : commentService.soredList("Top", id)) {
                if (!comment.isDeleted() && comment.getParentComment() == 0) {
                    comments.add(comment);
                }
            }
        } else if (sortType.equals("New")) {
            for (Comment comment : commentService.soredList("New", id)) {
                if (!comment.isDeleted() && comment.getParentComment() == 0) {
                    comments.add(comment);
                }
            }
        } else if (sortType.equals("Old")) {
            for (Comment comment : commentService.soredList("Old", id)) {
                if (!comment.isDeleted() && comment.getParentComment() == 0) {
                    comments.add(comment);
                }

            }
        }
        return new ResponseEntity<>(dtoService.commentToDTO(comments), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PostDTO>> findAll() {

        List<Post> posts = postService.findAll();
        List<Post> returnPosts = new ArrayList<>();

        for (Post post : posts) {
            if (!post.isDeleted())
                returnPosts.add(post);
        }
        List<PostDTO> returnPostsDTO = dtoService.postToDTO(returnPosts);
        Collections.shuffle(returnPosts);
        return new ResponseEntity<>(returnPostsDTO, HttpStatus.OK);
    }


    @PostMapping(value = "{id}/reactions")
    public ResponseEntity<ReactionDTO> UpvoteDownvote(@RequestHeader("Authorization") String
                                                              bearer, @RequestBody ReactionDTO reactionDTO, @PathVariable int id) {

        Post post = postService.findById(id);
        Community community = communityService.findById(post.getCommunity().getId());
        String username = tokenUtils.getUsernameFromToken(bearer);
        Reaction reaction = new Reaction(reactionDTO);
        User user = userService.findByUsername(username);
        reaction.setUser(user);
        reaction.setComment(null);
        reaction.setPost(post);
        for (Banned banned : community.getBanneds()) {
            if (banned.getUser().getId() == user.getId()) {
                return new ResponseEntity<>(new ReactionDTO(), HttpStatus.NOT_ACCEPTABLE);

            }
        }
        if (reactionService.alreadyVoted(user.getId(), post.getId()).isEmpty()) {
            reactionService.saveReaction(reaction);
        } else {
            return new ResponseEntity<>(new ReactionDTO(reaction), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(new ReactionDTO(reaction), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO
                                                      postDTO, @RequestHeader("Authorization") String bearer) {

        String username = tokenUtils.getUsernameFromToken(bearer);
        Community community = communityService.findById(postDTO.getCommunity().getId());
        User user = userService.findByUsername(username);
        Post post = new Post(postDTO);
        post.setUser(user);
        post.setCommunity(community);
        if (postDTO.getFlair() != null) {
            post.setFlair(flairService.findOne(postDTO.getFlair().getId()).get());
        } else {
            post.setFlair(null);
        }
        for (Banned banned : community.getBanneds()) {
            if (banned.getUser().getId() == user.getId()) {
                return new ResponseEntity<>(new PostDTO(), HttpStatus.NOT_ACCEPTABLE);

            }
        }
        Post returnPost = postService.save(post);
        return new ResponseEntity<>(new PostDTO(returnPost), HttpStatus.OK);
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
    public ResponseEntity<PostDTO> deletePost(@PathVariable int id) {

        Post post = postService.findById(id);
        post.setDeleted(true);
        postService.save(post);

        return new ResponseEntity<>(new PostDTO(post), HttpStatus.OK);
    }
}
