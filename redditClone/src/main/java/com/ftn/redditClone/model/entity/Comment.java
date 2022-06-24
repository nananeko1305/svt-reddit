package com.ftn.redditClone.model.entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ftn.redditClone.model.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    private String text;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "comment")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Reaction> reactions = new ArrayList<>();

    @OneToMany(mappedBy = "comment")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Report> reports = new ArrayList<>();

    public Comment(CommentDTO commentDTO){
        this.id = commentDTO.getId();
        this.text = commentDTO.getText();
        this.timestamp = commentDTO.getTimestamp();
        this.isDeleted = commentDTO.isDeleted();
        this.post = new Post(commentDTO.getPost());
        this.user = new User(commentDTO.getUser());
        for (ReactionDTO reactionDTO: commentDTO.getReactions()){
            reactions.add(new Reaction(reactionDTO));
        }
        for (ReportDTO reportDTO : commentDTO.getReports()){
            reports.add(new Report(reportDTO));
        }
    }
}
