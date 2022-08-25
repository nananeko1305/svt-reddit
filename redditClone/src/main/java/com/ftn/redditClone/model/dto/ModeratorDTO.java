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
    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }

    public ModeratorDTO(Moderator moderator){
        this.id = moderator.getId();
        if(moderator.getUser() == null){
            this.user = null;
        }else {
            this.user = new UserDTO(moderator.getUser());
        }
        if(moderator.getCommunity() == null){
            this.community = null;
        }
        if(moderator.isDeleted()){
            this.isDeleted = true;
        }else {
            this.isDeleted = false;
        }

    }

}
