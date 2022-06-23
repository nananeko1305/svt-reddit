package com.ftn.redditClone.model.dto;

import com.ftn.redditClone.model.entity.Community;
import com.ftn.redditClone.model.entity.Moderator;
import com.ftn.redditClone.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModeratorDTO {

    private int id;
    private UserDTO user;
    private CommunityDTO community;

    public ModeratorDTO(Moderator moderator){
        this.id = moderator.getId();
        this.user = new UserDTO(moderator.getUser());
        this.community = new CommunityDTO(moderator.getCommunity());
    }

}
