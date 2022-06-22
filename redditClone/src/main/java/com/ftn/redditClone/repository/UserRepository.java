package com.ftn.redditClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.redditClone.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUsername(String username);
	
}
