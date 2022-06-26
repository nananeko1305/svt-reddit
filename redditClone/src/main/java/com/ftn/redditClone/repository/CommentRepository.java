package com.ftn.redditClone.repository;

import com.ftn.redditClone.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
