package com.ftn.redditClone.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.redditClone.model.entity.Community;
import com.ftn.redditClone.repository.CommunityRepository;
import com.ftn.redditClone.service.CommunityService;

@Service
public class CommunityServiceImpl implements CommunityService{

	@Autowired
	private CommunityRepository communityRepository;
	
	@Override
	public List<Community> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Community community) {
		communityRepository.save(community);
		
	}

	@Override
	public void deleteById(int id) {
		communityRepository.deleteById(id);
		
	}

	@Override
	public Community findById(int id) {
		communityRepository.findById(id);
		return null;
	}

}
