package com.ftn.redditClone.model.dto;

import com.ftn.redditClone.model.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private int id;
    private String title;
    private String text;
    private LocalDate creationDate;
    private String imagePath;
    private CommunityDTO community;
    private UserDTO user;

    public PostDTO(Post post){
        this(post.getId(), post.getTitle(), post.getText(), post.getCreationDate(), post.getImagePath(), new CommunityDTO(post.getCommunity()), new UserDTO(post.getUser()));
    }
}
