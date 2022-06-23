package com.ftn.redditClone.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ftn.redditClone.model.dto.*;
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
@Table(name = "posts")
public class Post  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "creationDate")
    private LocalDate creationDate;

    @Column(name = "imagePath")
    private String imagePath;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "communityID", referencedColumnName = "id", nullable = false)
    private Community community;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userID", referencedColumnName = "id", nullable = false)// prvi atribut je u ovoj tabeli kolona a drugi je referenca na kolonu tabele od ispod
    private User user;//tabela

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flairId", referencedColumnName = "id", nullable = false)
    private Flair flair;

    @OneToMany(mappedBy = "post")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private Set<Reaction> reactions = new HashSet<>();

    public Post(PostDTO postDTO){
        this.id = postDTO.getId();
        this.title = postDTO.getTitle();
        this.text = postDTO.getText();
        this.creationDate = postDTO.getCreationDate();
        this.imagePath = postDTO.getImagePath();
        this.community = new Community(postDTO.getCommunity());
        this.user = new User(postDTO.getUser());
        this.flair = new Flair(postDTO.getFlair());
        for (ReportDTO reportDTO: postDTO.getReports()){
            this.reports.add(new Report(reportDTO));
        }
        for (ReactionDTO reactionDTO: postDTO.getReactions()){
            this.reactions.add(new Reaction(reactionDTO));
        }
    }

}
