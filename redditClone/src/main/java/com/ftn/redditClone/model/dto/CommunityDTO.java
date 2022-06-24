package com.ftn.redditClone.model.dto;

import com.ftn.redditClone.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    private List<ModeratorDTO> moderators = new ArrayList<>();
    private List<BannedDTO> banneds = new ArrayList<>();
    private List<RuleDTO> rules = new ArrayList<>();
    private Set<FlairDTO> flairs = new HashSet<>();
    public CommunityDTO(Community community) {
        this.id = community.getId();
        this.name = community.getName();
        this.description = community.getDescription();
        this.creationDate = community.getCreationDate();
        this.isSuspended = community.isSuspended();
        this.suspendedReason = community.getSuspendedReason();

        for (Moderator moderator : community.getModerators()) {
            this.moderators.add(new ModeratorDTO(moderator));
        }

        for (Banned banned : community.getBanneds()) {
            this.banneds.add(new BannedDTO(banned));
        }

        for (Rule rule : community.getRules()) {
            this.rules.add(new RuleDTO(rule));

        }
        for (Flair flair : community.getFlairs()) {
            this.flairs.add(new FlairDTO(flair));

        }
    }

}
