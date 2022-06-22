package com.ftn.redditClone.repository;

import com.ftn.redditClone.model.entity.Moderator;
import com.ftn.redditClone.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Integer> {

//    Moderator findByUsername(String username);
}
