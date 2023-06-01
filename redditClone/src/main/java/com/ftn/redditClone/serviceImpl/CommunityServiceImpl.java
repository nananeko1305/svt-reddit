package com.ftn.redditClone.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ftn.redditClone.elastic.dto.CommunityElasticDTO;
import com.ftn.redditClone.elastic.lucene.handlers.DocumentHandler;
import com.ftn.redditClone.elastic.lucene.handlers.PDFHandler;
import com.ftn.redditClone.model.entity.Post;
import com.ftn.redditClone.model.entity.Reaction;
import com.ftn.redditClone.model.entity.ReactionType;
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
	public double getAverageCarmaForCommunity(int communityId) {

		Community community = communityRepository.findById(communityId).get();

		if (community.getPosts().size() != 0) {
			double fullKarma = 0.0;

			for (Post post : community.getPosts()) {
				for (Reaction reaction : post.getReactions()) {
					if (reaction.getReactionType() == ReactionType.UPVOTE) {
						fullKarma++;
					} else {
						fullKarma--;
					}
				}
			}

			return fullKarma / community.getPosts().size();
		}else {
			return 0.0;
		}
	}

	@Override
	public List<Community> findAllRandom() {
		return communityRepository.findAllRandom();
	}




}
