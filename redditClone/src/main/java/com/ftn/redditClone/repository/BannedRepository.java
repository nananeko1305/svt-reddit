package com.ftn.redditClone.repository;

import com.ftn.redditClone.model.entity.Banned;
import com.ftn.redditClone.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannedRepository extends JpaRepository<Banned, Integer> {

    @Modifying
    @Query(value = "delete from jpa.banned b where b.id = ?1", nativeQuery = true)
    void delete(int id);

}
