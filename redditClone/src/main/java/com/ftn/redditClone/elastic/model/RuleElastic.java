package com.ftn.redditClone.elastic.model;

import com.ftn.redditClone.model.entity.Community;
import com.ftn.redditClone.model.entity.Rule;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "rules")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class RuleElastic {

    @Id
    private int id;

    @Field(type = FieldType.Text)
    private String description;

    public RuleElastic(Rule rule) {
        this.id = rule.getId();
        this.description = rule.getDescription();
    }
}
