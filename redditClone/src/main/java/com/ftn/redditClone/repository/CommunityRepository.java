package com.ftn.redditClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.redditClone.model.entity.Community;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {
    @Query(value = "select * from communities c order by RAND()", nativeQuery = true)
    List<Community> findAllRandom();
}
