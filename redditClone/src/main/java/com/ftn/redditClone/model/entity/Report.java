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

public class Report  {

    private ReportReason reportReason;
    private LocalDate timestamp;
    private User byUser;
    private boolean accepted;

    
}
