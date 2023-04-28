package com.ftn.redditClone.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.ftn.redditClone.elastic.dto.CommunityElasticDTO;
import com.ftn.redditClone.elastic.lucene.handlers.DocumentHandler;
import com.ftn.redditClone.elastic.lucene.handlers.PDFHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ftn.redditClone.model.entity.Community;
import com.ftn.redditClone.repository.CommunityRepository;
import com.ftn.redditClone.service.CommunityService;
import org.springframework.web.multipart.MultipartFile;

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
