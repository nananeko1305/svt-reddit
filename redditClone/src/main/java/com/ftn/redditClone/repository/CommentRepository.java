package com.ftn.redditClone.repository;

import com.ftn.redditClone.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "SELECT * from comments c where c.post_id = ?1 ORDER BY (select COUNT(*) from reaction r2 WHERE r2.comment_id = c.id) DESC ", nativeQuery = true)
    List<Comment> sortTop(int id);

    @Query(value = "select * from comments where post_id = ?1 order by timestamp", nativeQuery = true)
    List<Comment> sortOld(int id);

    @Query(value = "select * from comments where post_id = ?1 order by timestamp DESC", nativeQuery = true)
    List<Comment> sortNew(int id);



}
