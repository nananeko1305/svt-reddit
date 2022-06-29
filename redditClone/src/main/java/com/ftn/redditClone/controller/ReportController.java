package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.ReportDTO;
import com.ftn.redditClone.model.entity.Comment;
import com.ftn.redditClone.model.entity.Post;
import com.ftn.redditClone.model.entity.Report;
import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.service.CommentService;
import com.ftn.redditClone.service.PostService;
import com.ftn.redditClone.service.ReportService;
import com.ftn.redditClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reports")
public class ReportController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private ReportService reportService;

    @PostMapping()
    public ResponseEntity<ReportDTO> saveReport(@RequestBody ReportDTO reportDTO, @RequestHeader("Authorization") String bearer){
        Report report = new Report(reportDTO);
        Post post = new Post();
        Comment comment = new Comment();
        User user = userService.findById(reportDTO.getUser().getId());
        report.setUser(user);
        if(reportDTO.getPost().getId() != 0){
            report.setComment(null);
            post = postService.findById(reportDTO.getPost().getId());
            report.setPost(post);
            reportService.saveReport(report);
        }else {
            report.setPost(null);
            comment = commentService.findById(reportDTO.getComment().getId()).get();
            report.setComment(comment);
            reportService.saveReport(report);
        }
        return new ResponseEntity<>(new ReportDTO(report), HttpStatus.OK);
    }
}
