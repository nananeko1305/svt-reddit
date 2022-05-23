package com.ftn.redditClone.service;

import java.util.List;

import com.ftn.redditClone.model.entity.Community;

public interface CommunityService {

	public List<Community> findAll();
	
	public void save(Community community);
	
	public void deleteById(int id);
	
	public Community findById(int id);
}
