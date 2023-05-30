package com.ftn.redditClone.model.dto;

import lombok.Data;

@Data
public class MultipleValuesDTO {

    private String name;
    private String description;
    private double averageKarma;
    private Integer numberOfPosts;

}
