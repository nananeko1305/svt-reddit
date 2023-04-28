package com.ftn.redditClone.elastic.model;

import com.ftn.redditClone.model.entity.Rule;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "communities")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class CommunityElastic {

    @Id
    private Integer id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Integer)
    private Integer numberOfPosts;

    @Field(type = FieldType.Double)
    private Double averageKarma;

    @Field(type = FieldType.Text)
    private String pdfDescription;

    @Field(type = FieldType.Nested)
    private List<Rule> rules;

    private String filename;

}