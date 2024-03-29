package com.ftn.redditClone.model.entity;


import javax.persistence.*;

import com.ftn.redditClone.model.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "creationDate", nullable = false)
    private String creationDate;

    @Column(name = "isSuspended", nullable = false)
    public boolean isSuspended;

    @Column(name = "suspendedReason", nullable = true)
    public String suspendedReason;

    @OneToMany(mappedBy = "community")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Moderator> moderators = new ArrayList<>();

    @OneToMany(mappedBy = "community")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Banned> banneds = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Rule> rules = new ArrayList<>();

    @OneToMany(mappedBy = "community")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "flairs_communities", joinColumns = @JoinColumn(name = "flair_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "community_id", referencedColumnName = "id"))
    private List<Flair> flairs = new ArrayList<>();


    public Community(CommunityDTO communityDTO) {
        id = communityDTO.getId();
        name = communityDTO.getName();
        description = communityDTO.getDescription();
        creationDate = communityDTO.getCreationDate();
        isSuspended = communityDTO.isSuspended();
        suspendedReason = communityDTO.getSuspendedReason();
    }
}
