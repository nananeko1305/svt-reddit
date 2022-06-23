package com.ftn.redditClone.model.entity;

import com.ftn.redditClone.model.dto.CommunityDTO;
import com.ftn.redditClone.model.dto.ModeratorDTO;
import com.ftn.redditClone.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Moderator{

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userID", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "communityId", referencedColumnName = "id", nullable = false)
    private Community community;

    public Moderator(ModeratorDTO moderatorDTO){
        this.id = moderatorDTO.getId();
        this.user = new User(moderatorDTO.getUser());
        this.community = new Community(moderatorDTO.getCommunity());
    }
}
