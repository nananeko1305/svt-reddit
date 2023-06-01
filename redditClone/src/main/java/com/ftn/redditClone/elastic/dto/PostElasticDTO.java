package com.ftn.redditClone.elastic.dto;

import com.ftn.redditClone.elastic.model.CommentElastic;
import com.ftn.redditClone.elastic.model.FlairElastic;
import com.ftn.redditClone.model.entity.Flair;
import com.ftn.redditClone.model.entity.User;
import lombok.*;

import java.util.List;

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
    private Integer numberOfComments;
    private CommunityElasticDTO community;
    private FlairElastic flair;
    private List<CommentElastic> commentElasticList;
    private String pdfDescription;

}
