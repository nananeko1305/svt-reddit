package com.ftn.redditClone.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ftn.redditClone.model.dto.CommunityDTO;
import com.ftn.redditClone.model.dto.FlairDTO;
import com.ftn.redditClone.model.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flairs")
public class Flair  {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "flair")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(mappedBy = "flairs", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Community> communities = new HashSet<>();

    @Column(name = "isDeleted")
    private boolean isDeleted;

    public Flair(FlairDTO flairDTO){
        id = flairDTO.getId();
        name = flairDTO.getName();
    }
}
