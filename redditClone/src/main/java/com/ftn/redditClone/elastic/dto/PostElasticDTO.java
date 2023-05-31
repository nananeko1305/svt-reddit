package com.ftn.redditClone.elastic.dto;

import com.ftn.redditClone.model.entity.Flair;
import com.ftn.redditClone.model.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class PostElasticDTO {

    private Integer id;
    private String title;
    private String text;
    private Integer karma;
    private Double numberOfComments;
    private User user;
    private CommunityElasticDTO community;
    private Flair flair;
    private String pdfDescription;

}
