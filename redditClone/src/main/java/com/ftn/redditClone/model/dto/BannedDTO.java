package com.ftn.redditClone.model.dto;

import com.ftn.redditClone.model.entity.Banned;
import com.ftn.redditClone.model.entity.Moderator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannedDTO {

    private int id;
    private LocalDate timestamp;
    private ModeratorDTO moderator;
    private UserDTO user;
    private CommunityDTO community;

    public BannedDTO(Banned banned){
        this.id = banned.getId();
        this.timestamp = banned.getTimestamp();
        this.moderator = new ModeratorDTO(banned.getModerator());
        this.user = new UserDTO(banned.getUser());
        this.community = new CommunityDTO(banned.getCommunity());
    }
}
