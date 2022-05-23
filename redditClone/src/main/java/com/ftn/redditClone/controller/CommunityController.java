package com.ftn.redditClone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.redditClone.model.dto.CommunityDTO;
import com.ftn.redditClone.model.entity.Community;
import com.ftn.redditClone.service.CommunityService;

@RestController
@RequestMapping("community")
public class CommunityController {

	@Autowired
	private CommunityService communityService;
	
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@PostMapping(consumes = "application/json")
	public ResponseEntity<CommunityDTO> createCommunity(@RequestBody CommunityDTO communityDTO){
		
		Community community = new Community(communityDTO.getId(), communityDTO.getName(), communityDTO.getDescription(), communityDTO.getCreationDate(), communityDTO.isSuspended, communityDTO.suspendedReason);
		communityService.save(community);
		
		return new ResponseEntity<>(new CommunityDTO(communityDTO.getId(), communityDTO.getName(), communityDTO.getDescription(), communityDTO.getCreationDate(), communityDTO.isSuspended, communityDTO.suspendedReason), HttpStatus.CREATED);
		
	}
	
}
