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

public class Moderator implements Serializable {

    private String username;
    private String password;
    private String email;
    private String avatar;
    private LocalDate registrationDate;
    private String description;
    private String displayName;


}
