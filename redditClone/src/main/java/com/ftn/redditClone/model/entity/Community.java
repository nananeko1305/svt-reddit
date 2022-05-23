package com.ftn.redditClone.model.entity;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "communities")
public class Community {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
    private String description;

	@Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;

	@Column(name = "isSuspended", nullable = false)
    public boolean isSuspended;

	@Column(name = "suspendedReason", nullable = false)
    public String suspendedReason;

	public Community(String name, String description, LocalDate creationDate, boolean isSuspended, String suspendedReason) {
		this.name = name;
		this.description = description;
		this.creationDate = creationDate;
		this.isSuspended = isSuspended;
		this.suspendedReason = suspendedReason;
	}
}
