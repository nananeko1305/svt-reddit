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
    private CommunityDTO community;

    public RuleDTO(Rule rule) {
        this.id = rule.getId();
        this.description = rule.getDescription();
        this.community = new CommunityDTO(rule.getCommunity());
    }
}
