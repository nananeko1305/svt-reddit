package com.ftn.redditClone.model.dto;

import com.ftn.redditClone.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private FlairDTO flair;
    private List<ReportDTO> reports;
    private Set<ReactionDTO> reactions;

    public PostDTO(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.text = post.getText();
        this.creationDate = post.getCreationDate();
        this.imagePath = post.getImagePath();
        this.community = new CommunityDTO(post.getCommunity());
        this.user = new UserDTO(post.getUser());
        this.flair = new FlairDTO(post.getFlair());
        for (Report report: post.getReports()){
            this.reports.add(new ReportDTO(report));
        }
        for (Reaction reaction: post.getReactions()){
            this.reactions.add(new ReactionDTO(reaction));
        }
    }

}
