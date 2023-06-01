package com.ftn.redditClone.elastic.dto;

import com.ftn.redditClone.elastic.util.SearchType;
import lombok.Data;

@Data
public class MultipleValuesDTO {

    private String name;
    private String description;
    private double averageKarma;
    private Integer numberOfPosts;
    private Integer minPosts;
    private Integer maxPosts;
    private Integer minKarma;
    private Integer maxKarma;
    private String pdfDescription;
    private String rule;
    private String searchAccuracy;
    private SearchType searchType;

}
