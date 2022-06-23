package com.ftn.redditClone.model.entity;

import javax.persistence.*;

import com.ftn.redditClone.model.dto.CommunityDTO;
import com.ftn.redditClone.model.dto.RuleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "rule")
public class Rule {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "communityId", referencedColumnName = "id")
    private Community community;

    public Rule(RuleDTO ruleDTO) {
        this.id = ruleDTO.getId();
        this.description = ruleDTO.getDescription();
        this.community = new Community(ruleDTO.getCommunity());
    }

}
