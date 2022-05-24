package com.ftn.redditClone.model.dto;

import com.ftn.redditClone.model.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDTO {

	private int id;
	private String name;
    private String description;
    private LocalDate creationDate;
    public boolean isSuspended;
    public String suspendedReason;

    public CommunityDTO(String name, String description, LocalDate creationDate, boolean isSuspended, String suspendedReason) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.isSuspended = isSuspended;
        this.suspendedReason = suspendedReason;
    }

    public CommunityDTO(Community community){
        this(community.getName(), community.getDescription(), community.getCreationDate(), community.isSuspended, community.getSuspendedReason());
    }
}
