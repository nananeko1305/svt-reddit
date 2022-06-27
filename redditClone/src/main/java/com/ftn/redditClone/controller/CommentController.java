package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.CommentDTO;
import com.ftn.redditClone.model.dto.PostDTO;
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
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DTOService dtoService;

    @Autowired
    private ReactionService reactionService;

    @PostMapping(value = "{id}/reactions")
    public ResponseEntity<ReactionDTO> UpvoteDownvote(@RequestHeader("Authorization") String bearer, @RequestBody ReactionDTO reactionDTO, @PathVariable int id){
        String token = bearer.substring(7);
        String username = tokenUtils.getUsernameFromToken(token);
        Reaction reaction = new Reaction(reactionDTO);
        User user = userService.findByUsername(username);
        reaction.setUser(user);
        Comment comment = commentService.findById(id).get();
        reaction.setComment(comment);
        reaction.setPost(null);
        if(reactionService.alreadyVotedComment(user.getId(), comment.getId()).isEmpty()){
            reactionService.saveReaction(reaction);
        }
        return new ResponseEntity<>(new ReactionDTO(reaction), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<CommentDTO>> findAll(){
        return new ResponseEntity<>(dtoService.commentToDTO(commentService.findAll()), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/reactions")
    private ResponseEntity<List<ReactionDTO>> findReactionsForComment(@PathVariable int id){
        List<ReactionDTO> reactions = dtoService.reactionToDTO(commentService.findById(id).get().getReactions());
        return new ResponseEntity<>(reactions, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CommentDTO> saveComment(@RequestBody CommentDTO commentDTO, @RequestHeader("Authorization") String bearer){

        String token = bearer.substring(7);
        String username = tokenUtils.getUsernameFromToken(token);

        Comment comment = new Comment(commentDTO);
        User user = userService.findByUsername(username);
        comment.setUser(user);
        Post post = postService.findById(commentDTO.getPost().getId());
        comment.setPost(post);

        commentService.save(comment);

        return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.OK);
    }
}
