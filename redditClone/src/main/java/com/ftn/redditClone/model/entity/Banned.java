package com.ftn.redditClone.model.entity;

import java.time.LocalDate;

import com.ftn.redditClone.model.dto.BannedDTO;
import com.ftn.redditClone.model.dto.CommunityDTO;
import com.ftn.redditClone.model.dto.ModeratorDTO;
import com.ftn.redditClone.model.dto.UserDTO;
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
@Table(name = "banned")
public class Banned {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @OneToOne
    private Moderator moderator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "communityId", referencedColumnName = "id")
    private Community community;


    public Banned(BannedDTO bannedDTO) {
        id = bannedDTO.getId();
        timestamp = bannedDTO.getTimestamp();
        moderator = null;
        user = null;
        community = null;
    }
}


