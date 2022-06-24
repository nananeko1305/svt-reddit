package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.ReactionDTO;
import com.ftn.redditClone.model.entity.Comment;
import com.ftn.redditClone.model.entity.Post;
import com.ftn.redditClone.model.entity.Reaction;
import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.DTOService;
import com.ftn.redditClone.service.PostService;
import com.ftn.redditClone.service.ReactionService;
import com.ftn.redditClone.service.UserService;
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

    @PostMapping()
    private ResponseEntity<ReactionDTO> saveReaction(@RequestHeader("Authorization") String bearer, @RequestBody ReactionDTO reactionDTO) {

        String token = bearer.substring(7);
        String username = tokenUtils.getUsernameFromToken(token);
        //podaci za save
        Reaction reaction = new Reaction(reactionDTO);
        reaction.setUser(userService.findByUsername(username));
        Post post = postService.findById(reactionDTO.getPost().getId());
        reaction.setPost(post);
        reaction.setComment(null);
        reactionService.saveReaction(reaction);

        return new ResponseEntity<>(new ReactionDTO(reaction), HttpStatus.OK);
    }



}
