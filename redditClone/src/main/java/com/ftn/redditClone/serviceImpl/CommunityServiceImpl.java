package com.ftn.redditClone.serviceImpl;

import java.util.List;
import java.util.Optional;

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
		return communityRepository.findAll();
	}

	@Override
	public Community save(Community community) {
		return communityRepository.save(community);
		
	}

	@Override
	public void deleteById(int id) {
		communityRepository.deleteById(id);
		
	}

	@Override
	public Community findById(int id) {
		return communityRepository.findById(id).orElseGet(null);
	}

	@Override
	public Community updateCommunity(Community community) {
		return communityRepository.save(community);
	}

	@Override
	public List<Community> findAllRandom() {
		return communityRepository.findAllRandom();
	}


}
