package com.ftn.redditClone.service;

import com.ftn.redditClone.model.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findAll();
    void save(Comment Comment);
    void deleteById(int id);
    Optional<Comment> findById(int id);
    Comment updateComment(Comment Comment);

    List<Comment> soredList(String sortType, int id);

    List<Comment> replies(int id);

}
