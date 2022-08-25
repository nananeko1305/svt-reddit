package com.ftn.redditClone.repository;

import com.ftn.redditClone.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT * from posts p ORDER BY (select COUNT(*) from reaction r WHERE r.post_id = p.id) DESC", nativeQuery = true)
    List<Post> sortedTop();

    @Query(value = "SELECT * from posts p ORDER BY (select COUNT(*) from reaction r WHERE r.post_id = p.id order by r.timestamp)  DESC, p.creation_date DESC  ", nativeQuery = true)
    List<Post> sortedHot();

    @Query(value = "SELECT * from posts p ORDER BY (select COUNT(*) from reaction r WHERE r.post_id = p.id and r.reaction_type = 0) DESC", nativeQuery = true)
    List<Post> sortedUpvote();

    @Query(value = "SELECT * from posts p ORDER BY (select COUNT(*) from reaction r WHERE r.post_id = p.id and r.reaction_type = 1) DESC", nativeQuery = true)
    List<Post> sortedDownvote();

    @Query(value = "SELECT * from posts p where p.communityid  = ?1 ORDER BY (select COUNT(*) from reaction r WHERE r.post_id = p.id) DESC", nativeQuery = true)
    List<Post> sortedTopForCommunity(int id);

    @Query(value = "SELECT * from posts p where p.communityid = ?1 ORDER BY (select COUNT(*) from reaction r WHERE r.post_id = p.id order by r.timestamp) DESC, p.creation_date DESC ", nativeQuery = true)
    List<Post> sortedHotForCommunity(int id);
}
