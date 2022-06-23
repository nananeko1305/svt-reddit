package com.ftn.redditClone.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.ftn.redditClone.model.dto.CommentDTO;
import com.ftn.redditClone.model.dto.PostDTO;
import com.ftn.redditClone.model.dto.ReactionDTO;
import com.ftn.redditClone.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "reaction")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "reactionType")
    private ReactionType reactionType;
    @Column(name = "timestamp")
    private LocalDate timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commentId", referencedColumnName = "id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;


    public Reaction(ReactionDTO reactionDTO){
        this.id = reactionDTO.getId();
        this.reactionType = reactionDTO.getReactionType();
        this.timestamp = reactionDTO.getTimestamp();
        this.user = new User(reactionDTO.getUser());
        this.comment = new Comment(reactionDTO.getComment());
        this.post = new Post(reactionDTO.getPost());
    }
}
