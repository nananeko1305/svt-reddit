package com.ftn.redditClone.elastic.model;

import com.ftn.redditClone.model.entity.Flair;
import com.ftn.redditClone.model.entity.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "posts")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class PostElastic {

    @Id
    private Integer id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String text;

    @Field(type = FieldType.Integer)
    private Integer karma;

    @Field(type = FieldType.Integer)
    private Integer numberOfComments;

    @Field(type = FieldType.Object)
    private User user;

    @Field(type = FieldType.Object)
    private CommunityElastic community;

    @Field(type = FieldType.Object)
    private Flair flair;

    @Field(type = FieldType.Text)
    private String pdfDescription;

    private String filename;


}
