package com.ftn.redditClone.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.ftn.redditClone.model.dto.CommunityDTO;
import com.ftn.redditClone.model.dto.FlairDTO;
import com.ftn.redditClone.model.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<Post> posts;

    @ManyToMany(mappedBy = "flairs")
    private Set<Community> communities;

    public Flair(FlairDTO flairDTO){
        this.id = flairDTO.getId();
        this.name = flairDTO.getName();
        for(PostDTO postDTO : flairDTO.getPosts()){
            this.posts.add(new Post(postDTO));
        }
        for(CommunityDTO communityDTO : flairDTO.getCommunities()){
            this.communities.add(new Community(communityDTO));
        }
    }
}
