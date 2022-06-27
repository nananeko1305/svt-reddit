package com.ftn.redditClone.model.dto;
import com.ftn.redditClone.model.entity.Community;
import com.ftn.redditClone.model.entity.Flair;
import com.ftn.redditClone.model.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlairDTO {

    private int id;
    private String name;
    private boolean isDeleted;

    public FlairDTO(Flair flair){
        this.id = flair.getId();
        this.name = flair.getName();
        this.isDeleted = flair.isDeleted();

    }
}
