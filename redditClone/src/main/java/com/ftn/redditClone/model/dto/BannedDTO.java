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
    private String timestamp;
    private ModeratorDTO moderator;
    private UserDTO user;
    private CommunityDTO community;

    public BannedDTO(Banned banned){
        this.id = banned.getId();
        this.timestamp = banned.getTimestamp();
        if(moderator == null){
            moderator = null;
        }else{
            this.moderator = new ModeratorDTO(banned.getModerator());
        }
        if(user == null){
            user = null;
        }else{
            this.user = new UserDTO(banned.getUser());
        }
        if(community == null){
            community = null;
        }else{
            this.community = new CommunityDTO(banned.getCommunity());
        }

    }
}
