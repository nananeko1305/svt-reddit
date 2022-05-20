package com.ftn.redditClone.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Post  {

    private String title;
    private String text;
    private LocalDate creationDate;
    private String imagePath;

}
