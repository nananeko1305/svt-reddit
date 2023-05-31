package com.ftn.redditClone.elastic.model;

import com.ftn.redditClone.model.entity.Comment;
import com.ftn.redditClone.model.entity.Flair;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document(indexName = "flairs")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class FlairElastic {

    @Id
    private int id;

    @Field(type = FieldType.Text)
    private String name;

    public FlairElastic(Flair name){
        this.id = name.getId();
        this.name = name.getName();
    }
}
