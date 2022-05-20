package com.ftn.redditClone.model.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Banned {

    private LocalDate timestamp;
    private Moderator by;
    private String username;

}


