package com.ftn.redditClone.elastic.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class CommunityElasticDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer numberOfPosts;
    private Double averageKarma;
    private String pdfDescription;


}
