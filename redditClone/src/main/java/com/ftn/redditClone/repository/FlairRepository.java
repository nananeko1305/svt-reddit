package com.ftn.redditClone.repository;

import com.ftn.redditClone.model.entity.Flair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlairRepository extends JpaRepository<Flair, Integer> {
}
