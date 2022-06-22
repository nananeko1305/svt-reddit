package com.ftn.redditClone.model.entity;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

	@Column(name = "suspendedReason", nullable = true)
    public String suspendedReason;

	@OneToMany(mappedBy = "community", orphanRemoval = true, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Moderator> moderator;

	@OneToMany(mappedBy = "community", orphanRemoval = true, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Post> posts;

}
