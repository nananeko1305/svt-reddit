package com.ftn.redditClone.elastic.model;

import com.ftn.redditClone.model.entity.Comment;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document(indexName = "comments")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class CommentElastic {

    @Id
    private int id;

    @Field(type = FieldType.Text)
    private String text;

    public CommentElastic(Comment comment){
        this.id = comment.getId();
        this.text = comment.getText();
    }

}
