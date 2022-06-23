package com.ftn.redditClone.model.dto;

import com.ftn.redditClone.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDTO {

    private int id;
    private ReactionType reactionType;
    private LocalDate timestamp;
    public UserDTO user;
    public CommentDTO comment;
    public PostDTO post;

    public ReactionDTO(Reaction reaction){
        this.id = reaction.getId();
        this.reactionType = reaction.getReactionType();
        this.timestamp = reaction.getTimestamp();
        this.user = new UserDTO(reaction.getUser());
        this.comment = new CommentDTO(reaction.getComment());
        this.post = new PostDTO(reaction.getPost());
    }

}
