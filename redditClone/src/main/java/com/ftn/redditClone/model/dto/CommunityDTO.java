package com.ftn.redditClone.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDTO {

	private long id;
	private String name;
    private String description;
    private String creationDate;
    public boolean isSuspended;
    public String suspendedReason;

}
