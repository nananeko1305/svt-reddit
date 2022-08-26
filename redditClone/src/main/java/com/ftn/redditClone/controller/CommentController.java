package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.CommentDTO;
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

    @Autowired
    private CommunityService communityService;

    @PostMapping(value = "{id}/reactions")
    public ResponseEntity<ReactionDTO> UpvoteDownvote(@RequestHeader("Authorization") String bearer, @RequestBody ReactionDTO reactionDTO, @PathVariable int id){

        String username = tokenUtils.getUsernameFromToken(bearer);
        Reaction reaction = new Reaction(reactionDTO);
        User user = userService.findByUsername(username);
        reaction.setUser(user);
        Comment comment = commentService.findById(id).get();
        reaction.setComment(comment);
        reaction.setPost(null);
        if(reactionService.alreadyVotedComment(user.getId(), comment.getId()).isEmpty()){
            reactionService.saveReaction(reaction);
            return new ResponseEntity<>(new ReactionDTO(reaction), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ReactionDTO(reaction), HttpStatus.ACCEPTED);
        }
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

    @GetMapping(value = "{id}")
    private ResponseEntity<CommentDTO> findOne(@PathVariable int id){
         Comment comment = commentService.findById(id).get();
         return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.OK);
    }

    @GetMapping("{id}/replies")
    public ResponseEntity<List<CommentDTO>> repliesToComment(@PathVariable int id){
        return new ResponseEntity<>(dtoService.commentToDTO(commentService.replies(id)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CommentDTO> saveComment(@RequestBody CommentDTO commentDTO, @RequestHeader("Authorization") String bearer){

        String username = tokenUtils.getUsernameFromToken(bearer);
        Comment comment = new Comment(commentDTO);
        User user = userService.findByUsername(username);
        comment.setUser(user);
        Post post = postService.findById(commentDTO.getPost().getId());
        comment.setPost(post);
        Community community = communityService.findById(post.getCommunity().getId());
        for (Banned banned : community.getBanneds()) {
            if (banned.getUser().getId() == user.getId()) {
                return new ResponseEntity<>(new CommentDTO(), HttpStatus.NOT_ACCEPTABLE);

            }
        }
        commentService.save(comment);

        return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable int id){
        Comment comment = commentService.findById(id).get();
        comment.setDeleted(true);
        commentService.save(comment);
        return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.OK);
    }
}
