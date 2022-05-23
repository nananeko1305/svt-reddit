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

public class Comment {

    private String text;
    private LocalDate timestamp;
    private boolean isDeleted;

}
