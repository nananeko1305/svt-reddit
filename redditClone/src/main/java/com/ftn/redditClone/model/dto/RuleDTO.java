package com.ftn.redditClone.model.dto;

import com.ftn.redditClone.model.entity.Community;
import com.ftn.redditClone.model.entity.Rule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RuleDTO {

    private int id;
    private String description;
    private int community;
    private boolean isDeleted;

    public RuleDTO(Rule rule) {
        this.id = rule.getId();
        this.description = rule.getDescription();
        if(rule.getCommunity() == null){
            this.community = 0;
        }else {
            this.community = rule.getCommunity().getId();
        }
        this.isDeleted = rule.isDeleted();
    }
}
