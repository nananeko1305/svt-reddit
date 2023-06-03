package com.ftn.redditClone.elastic.model;

import com.ftn.redditClone.model.entity.Rule;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleElastic {

    private String description;

    public RuleElastic(Rule rule) {
        this.description = rule.getDescription();
    }
}
