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

    public PostDTO(String title, String text, LocalDate creationDate, String imagePath) {
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
        this.imagePath = imagePath;
    }

    public PostDTO(Post post){
        this(post.getId(), post.getTitle(), post.getText(), post.getCreationDate(), post.getImagePath());
    }
}
