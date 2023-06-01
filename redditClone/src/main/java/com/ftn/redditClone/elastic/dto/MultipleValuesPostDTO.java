package com.ftn.redditClone.elastic.dto;

import com.ftn.redditClone.elastic.util.SearchType;
import lombok.Data;

@Data
public class MultipleValuesPostDTO {

    private String title;
    private String text;
    private String flair;
    private String commentWordFind;
    private Integer minComments;
    private Integer maxComments;
    private Integer minKarma;
    private Integer maxKarma;
    private String pdfDescription;
    private String searchAccuracy;
    private SearchType searchType;

}
