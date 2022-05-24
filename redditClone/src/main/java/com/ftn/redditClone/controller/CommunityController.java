package com.ftn.redditClone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.redditClone.model.dto.CommunityDTO;
import com.ftn.redditClone.model.entity.Community;
import com.ftn.redditClone.service.CommunityService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;


    @GetMapping(consumes = "application/json")
    public ResponseEntity<CommunityDTO> findOne(@RequestBody CommunityDTO communityDTO){
        Community community = communityService.findById(communityDTO.getId());
        return new ResponseEntity<>(new CommunityDTO(community), HttpStatus.FOUND);
    }

    @GetMapping(value = "findAll")
    public ResponseEntity<List<CommunityDTO>> findAll(){

        List<Community> communities = communityService.findAll();
        List<CommunityDTO> returnCommunities = new ArrayList<CommunityDTO>();

        for (Community community:
             communities) {
            returnCommunities.add(new CommunityDTO(community));
        }

        return new ResponseEntity<>(returnCommunities, HttpStatus.OK);
    }

    //	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<CommunityDTO> createCommunity(@RequestBody CommunityDTO communityDTO) {

        Community community = new Community(communityDTO.getName(), communityDTO.getDescription(), communityDTO.getCreationDate(), communityDTO.isSuspended, communityDTO.suspendedReason);
        communityService.save(community);

        return new ResponseEntity<>(new CommunityDTO(community), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<CommunityDTO> updateCommunity(@RequestBody CommunityDTO communityDTO) {

        if (communityDTO.getId() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Community community = communityService.findById(communityDTO.getId());

            if (community.getName() != null) {
                community.setName(communityDTO.getName());
            }

            if (community.getDescription() != null) {
                community.setDescription(communityDTO.getDescription());
            }
            if (community.getSuspendedReason() != null) {
                community.setSuspendedReason(communityDTO.suspendedReason);
            }
            communityService.save(community);
            return new ResponseEntity<>(new CommunityDTO(community), HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value = "delete", consumes = "application/json")
    public void deleteCommunity(@RequestBody CommunityDTO communityDTO){
        communityService.deleteById(communityDTO.getId());
    }

}
