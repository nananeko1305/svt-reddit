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
    @JoinColumn(name = "commentId", referencedColumnName = "id", nullable = true)
    private Comment comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postId", referencedColumnName = "id", nullable = true)
    private Post post;

    public Reaction(ReactionDTO reactionDTO){
        id = reactionDTO.getId();
        reactionType = reactionDTO.getReactionType();
        timestamp = reactionDTO.getTimestamp();
        user = new User();
        comment = new Comment();
        post = new Post();
    }
}
