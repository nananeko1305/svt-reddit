package com.ftn.redditClone.repository;

import com.ftn.redditClone.model.entity.Moderator;
import com.ftn.redditClone.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Integer> {

    @Query(value = "select * from moderator m WHERE userid = ?1 and community_id = ?2", nativeQuery = true)
    Moderator findByUserId(int id, int communityId);
}
