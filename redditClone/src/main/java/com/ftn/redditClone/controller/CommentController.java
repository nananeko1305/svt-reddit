package com.ftn.redditClone.controller;

import com.ftn.redditClone.elastic.model.CommentElastic;
import com.ftn.redditClone.elastic.model.PostElastic;
import com.ftn.redditClone.elastic.service.CommunityElasticService;
import com.ftn.redditClone.elastic.service.PostElasticService;
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

    @Autowired
    private CommunityElasticService communityElasticService;

    @Autowired
    private PostElasticService postElasticService;



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

    @PostMapping()
    public ResponseEntity<CommentDTO> saveComment(@RequestBody CommentDTO commentDTO, @RequestHeader("Authorization") String bearer){


        String token = bearer.substring(7);
        String username = tokenUtils.getUsernameFromToken(token);

        Comment comment = new Comment(commentDTO);
        User user = userService.findByUsername(username);
        comment.setUser(user);
        Post post = postService.findById(commentDTO.getPost().getId());
        comment.setPost(post);
        Community community = communityService.findById(post.getCommunity().getId());
        for (Banned banned : community.getBanneds()) {
            if (banned.getUser().getId() == user.getId()) {
                return new ResponseEntity<>(new CommentDTO(), HttpStatus.OK);

            }
        }
        comment = commentService.save(comment);
        PostElastic postElastic = postElasticService.getOnePost(comment.getPost().getId());
        CommentElastic commentElastic = new CommentElastic(comment);
        List<CommentElastic> commentElastics = postElastic.getCommentElasticList();
        commentElastics.add(commentElastic);
        postElastic.setCommentElasticList(commentElastics);
        if (postElastic.getNumberOfComments() == null) {
            postElastic.setNumberOfComments(1);
        }else {
            postElastic.setNumberOfComments(postElastic.getNumberOfComments() + 1);
        }
        postElasticService.savePostElastic(postElastic);

        return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.OK);
    }

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
    @DeleteMapping("{id}")
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable int id){
        Comment comment = commentService.findById(id).get();
        comment.setDeleted(true);
        commentService.save(comment);
        return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.OK);
    }
}
