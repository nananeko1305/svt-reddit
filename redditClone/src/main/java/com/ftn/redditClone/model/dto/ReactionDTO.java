package com.ftn.redditClone.model.dto;

import com.ftn.redditClone.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDTO {

    private int id;
    private ReactionType reactionType;
    private String timestamp;
    public UserDTO user;
    public CommentDTO comment;
    public PostDTO post;

    public ReactionDTO(Reaction reaction){
        this.id = reaction.getId();
        this.reactionType = reaction.getReactionType();
        this.timestamp = reaction.getTimestamp();
        this.user = new UserDTO(reaction.getUser());
        if(reaction.getComment() != null)
            this.comment = new CommentDTO(reaction.getComment());
        if(reaction.getPost() != null)
            this.post = new PostDTO(reaction.getPost());
    }
}
