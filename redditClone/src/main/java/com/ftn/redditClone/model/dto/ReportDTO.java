package com.ftn.redditClone.model.dto;

import com.ftn.redditClone.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportDTO {

    private int id;
    private ReportReason reportReason;
    private String timestamp;
    private boolean accepted;
    private UserDTO user;
    private PostDTO post;
    private CommentDTO comment;

    public ReportDTO(Report report){
        this.id = report.getId();
        this.reportReason = report.getReportReason();
        this.timestamp = report.getTimestamp();
        this.accepted = report.isAccepted();
        this.user = new UserDTO(report.getUser());
        if(report.getPost() == null){
            this.post = null;
        }else{
            this.post = new PostDTO(report.getPost());
        }
        if(report.getComment() == null){
            this.comment = null;
        }else {
            this.comment = new CommentDTO(report.getComment());
        }

    }
}
