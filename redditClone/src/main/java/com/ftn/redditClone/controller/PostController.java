package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.FlairDTO;
import com.ftn.redditClone.model.dto.PostDTO;
import com.ftn.redditClone.model.entity.Community;
import com.ftn.redditClone.model.entity.Flair;
import com.ftn.redditClone.model.entity.Post;
import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.CommunityService;
import com.ftn.redditClone.service.DTOService;
import com.ftn.redditClone.service.PostService;
import com.ftn.redditClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    @GetMapping(consumes = "application/json")
    public ResponseEntity<PostDTO> getOne(@RequestBody PostDTO postDTO){

        Post post = postService.findById(postDTO.getId());
        postService.save(post);

        return new ResponseEntity<>(new PostDTO(), HttpStatus.FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<PostDTO>> findAll(){

        List<Post> posts = postService.findAll();
        List<PostDTO> returnPosts = new ArrayList<>();

        for (Post post :
                posts) {
            returnPosts.add(new PostDTO());
        }
        return new ResponseEntity<>(returnPosts, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @RequestHeader("Token") String bearer){

        String token = bearer.substring(7);
        String username = tokenUtils.getUsernameFromToken(token);
        Flair flair = new Flair();
        Post post = new Post(postDTO.getId(),postDTO.getTitle(), postDTO.getText(), LocalDate.now(), postDTO.getImagePath(), communityService.findById(postDTO.getCommunity().getId()), userService.findByUsername(username), new Flair(postDTO.getFlair()), null, null);
        postService.save(post);
        return new ResponseEntity<>(new PostDTO(), HttpStatus.CREATED);
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

    @DeleteMapping(consumes = "application/json")
    public void deletePost(@RequestBody PostDTO postDTO){
        postService.deleteById(postDTO.getId());
    }
}
