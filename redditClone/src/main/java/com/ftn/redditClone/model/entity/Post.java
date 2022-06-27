package com.ftn.redditClone.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ftn.redditClone.model.dto.*;
import com.ftn.redditClone.service.CommunityService;
import com.ftn.redditClone.service.PostService;
import com.ftn.redditClone.serviceImpl.CommunityServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

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
    @JoinColumn(name = "userID", referencedColumnName = "id", nullable = false)
// prvi atribut je u ovoj tabeli kolona a drugi je referenca na kolonu tabele od ispod
    private User user;//tabela

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flairId", referencedColumnName = "id", nullable = true)
    private Flair flair;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Reaction> reactions = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "isDeleted")
    private boolean isDeleted;

    public Post(PostDTO postDTO) {

        title = postDTO.getTitle();
        text = postDTO.getText();
        creationDate = postDTO.getCreationDate();
        imagePath = postDTO.getImagePath();
        community = null;
        user = null;
        flair = null;
    }

}
