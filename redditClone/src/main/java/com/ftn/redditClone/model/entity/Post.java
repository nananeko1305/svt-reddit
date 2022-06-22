package com.ftn.redditClone.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    public Community community;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userID", referencedColumnName = "id", nullable = false)// prvi atribut je u ovoj tabeli kolona a drugi je referenca na kolonu tabele od ispod
    public User user;//tabela

}
