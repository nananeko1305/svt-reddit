package com.ftn.redditClone.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.ftn.redditClone.model.dto.CommentDTO;
import com.ftn.redditClone.model.dto.PostDTO;
import com.ftn.redditClone.model.dto.ReportDTO;
import com.ftn.redditClone.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reports")
public class Report  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "reportReason")
    private ReportReason reportReason;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @Column(name = "accepted")
    private boolean accepted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commentId", referencedColumnName = "id")
    private Comment comment;

    public Report(ReportDTO reportDTO){
        id = reportDTO.getId();
        reportReason = reportDTO.getReportReason();
        timestamp = reportDTO.getTimestamp();
        accepted = reportDTO.isAccepted();
    }
}
