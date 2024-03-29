package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.ReactionDTO;
import com.ftn.redditClone.model.entity.Comment;
import com.ftn.redditClone.model.entity.Post;
import com.ftn.redditClone.model.entity.Reaction;
import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("reactions")
public class ReactionController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private PostService postService;

    @Autowired
    private DTOService dtoService;

    @Autowired
    private CommentService commentService;


    @PostMapping()
    private ResponseEntity<ReactionDTO> saveReaction(@RequestHeader("Authorization") String bearer, @RequestBody ReactionDTO reactionDTO) {

        String username = tokenUtils.getUsernameFromToken(bearer);
        //podaci za save
        Reaction reaction = new Reaction(reactionDTO);
        reaction.setUser(userService.findByUsername(username));
        if(reactionDTO.getPost() != null){
            Post post = postService.findById(reactionDTO.getPost().getId());
            reaction.setPost(post);
            reaction.setComment(null);
        }else{
            Comment comment = commentService.findById(reactionDTO.getComment().getId()).get();
            reaction.setComment(comment);
            reaction.setPost(null);
        }

        reactionService.saveReaction(reaction);

        return new ResponseEntity<>(new ReactionDTO(reaction), HttpStatus.OK);
    }



}
