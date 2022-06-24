package com.ftn.redditClone.model.dto;

import com.ftn.redditClone.model.entity.Comment;
import com.ftn.redditClone.model.entity.Reaction;
import com.ftn.redditClone.model.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private int id;
    private String text;
    private LocalDate timestamp;
    private boolean isDeleted;
    private PostDTO post;
    private UserDTO user;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.timestamp = comment.getTimestamp();
        this.isDeleted = comment.isDeleted();
        this.post = new PostDTO(comment.getPost());
        this.user = new UserDTO(comment.getUser());
    }
}
