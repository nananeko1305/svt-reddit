package com.ftn.redditClone.repository;

import com.ftn.redditClone.model.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {

    @Query(value = "SELECT * FROM reaction r WHERE r.user_id = ?1 AND r.post_id = ?2", nativeQuery = true)
    List<Reaction> alreadyVoted(int userID, int postID);


}
