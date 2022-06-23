package com.ftn.redditClone.model.entity;

import javax.persistence.*;

import com.ftn.redditClone.model.dto.*;
import org.aspectj.weaver.tools.Trace;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    //    @OneToOne(fetch = FetchType.LAZY)
    @Column(name = "role")
    private Role role;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "avatar", nullable = false)
    private String avatar;

    @Column(name = "registrationDate", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "displayName", nullable = false)
    private String displayName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Banned> banneds = new ArrayList<>();

    public User(UserDTO userDTO){

        this.id = userDTO.getId();
        this.username = userDTO.getUsername();
        this.role = userDTO.getRole();
        this.password = userDTO.getPassword();
        this.email = userDTO.getEmail();
        this.avatar = userDTO.getAvatar();
        this.registrationDate = userDTO.getRegistrationDate();
        this.description = userDTO.getDescription();
        this.displayName = userDTO.getDisplayName();

        for (PostDTO postDTO: userDTO.getPosts()){
            this.posts.add(new Post(postDTO));
        }

        for (CommentDTO commentDTO: userDTO.getComments()){
            this.comments.add(new Comment(commentDTO));
        }

        for (ReportDTO reportDTO: userDTO.getReports()){
            this.reports.add(new Report(reportDTO));
        }

        for (BannedDTO bannedDTO: userDTO.getBanneds()){
            this.banneds.add(new Banned(bannedDTO));
        }
    }

}
