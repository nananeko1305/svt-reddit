package com.ftn.redditClone.service;

import java.time.LocalDate;
import java.util.List;

import com.ftn.redditClone.model.entity.Community;

public interface CommunityService {

	public List<Community> findAll();
	
	public Community save(Community community);
	
	public void deleteById(int id);
	
	public Community findById(int id);

	public Community updateCommunity(Community community);

	List<Community> findAllRandom();
}
