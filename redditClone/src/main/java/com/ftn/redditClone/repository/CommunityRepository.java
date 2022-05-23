package com.ftn.redditClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.redditClone.model.entity.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer>{

}
