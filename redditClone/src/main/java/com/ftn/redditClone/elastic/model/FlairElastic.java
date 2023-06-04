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
public class FlairElastic {

    private String name;

    public FlairElastic(Flair name){
        this.name = name.getName();
    }
}