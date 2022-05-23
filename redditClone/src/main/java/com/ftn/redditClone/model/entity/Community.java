package com.ftn.redditClone.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "communities")
public class Community {

	@Id
	@Column(name = "id", nullable = false)
	private long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "description", nullable = false)
    private String description;
	@Column(name = "creationDate", nullable = false)
    private String creationDate;
	@Column(name = "isSuspended", nullable = false)
    public boolean isSuspended;
	@Column(name = "suspendedReason", nullable = false)
    public String suspendedReason;

}
