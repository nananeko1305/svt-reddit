package com.ftn.redditClone.model.dto;

import com.ftn.redditClone.model.entity.*;
import com.ftn.redditClone.service.DTOService;
import com.ftn.redditClone.serviceImpl.DTOServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDTO {

    private int id;
    private String name;
    private String description;
    private LocalDate creationDate;
    private boolean isSuspended;
    private String suspendedReason;
    private List<FlairDTO> flairs = new ArrayList<>();

    private List<RuleDTO> rules = new ArrayList<>();


    public CommunityDTO(Community community) {
        DTOService dtoService = new DTOServiceImpl();
        this.id = community.getId();
        this.name = community.getName();
        this.description = community.getDescription();
        this.creationDate = community.getCreationDate();
        this.isSuspended = community.isSuspended();
        this.suspendedReason = community.getSuspendedReason();
        if(community.getFlairs().isEmpty()){
            this.flairs = null;
        }else {
            this.flairs = dtoService.flairToDTO(community.getFlairs());
        }
        if(community.getRules().isEmpty()){
            this.rules = null;
        }else {
            this.rules = dtoService.ruleToDTO(community.getRules());
        }
    }

}
