package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.PostDTO;
import com.ftn.redditClone.model.entity.Post;
import com.ftn.redditClone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(consumes = "application/json")
    public ResponseEntity<PostDTO> getOne(@RequestBody PostDTO postDTO){

        Post post = postService.findById(postDTO.getId());
        postService.save(post);

        return new ResponseEntity<>(new PostDTO(post), HttpStatus.FOUND);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){

        Post post = new Post(postDTO.getTitle(), postDTO.getText(), postDTO.getCreationDate(), postDTO.getImagePath());
        postService.save(post);

        return new ResponseEntity<>(new PostDTO(post), HttpStatus.CREATED);
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

        return new ResponseEntity<>(new PostDTO(post), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(consumes = "application/json")
    public void deletePost(@RequestBody PostDTO postDTO){
        postService.deleteById(postDTO.getId());
    }
}
