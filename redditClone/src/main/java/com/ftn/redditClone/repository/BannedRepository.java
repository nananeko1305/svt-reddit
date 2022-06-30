package com.ftn.redditClone.repository;

import com.ftn.redditClone.model.entity.Banned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannedRepository extends JpaRepository<Banned, Integer> {

}
