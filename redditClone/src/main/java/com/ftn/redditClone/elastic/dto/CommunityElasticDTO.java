package com.ftn.redditClone.elastic.dto;

import com.ftn.redditClone.elastic.model.CommunityElastic;
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

    public CommunityElasticDTO(CommunityElastic communityElastic){
        this.id = communityElastic.getId();
        this.name = communityElastic.getName();
        this.description = communityElastic.getDescription();
        this.numberOfPosts = communityElastic.getNumberOfPosts();
        this.averageKarma = communityElastic.getAverageKarma();
        this.pdfDescription = communityElastic.getPdfDescription();
    }

}
