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
    private int userID;
    private int communityID;
    private boolean isDeleted;

    public ModeratorDTO(Moderator moderator){
        this.id = moderator.getId();
        this.userID = moderator.getUser().getId();
        this.communityID = moderator.getCommunity().getId();
        this.isDeleted = moderator.isDeleted();
    }

}
