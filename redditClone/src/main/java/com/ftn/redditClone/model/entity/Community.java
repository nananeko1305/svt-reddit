package com.ftn.redditClone.model.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Community {

    private String name;
    private String description;
    private String creationDate;
    public boolean isSuspended;
    public String suspendedReason;

}
