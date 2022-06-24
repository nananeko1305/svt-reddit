package com.ftn.redditClone.controller;

import antlr.Token;
import com.ftn.redditClone.model.dto.PostDTO;
import com.ftn.redditClone.model.entity.Moderator;
import com.ftn.redditClone.model.entity.Post;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.redditClone.model.dto.CommunityDTO;
import com.ftn.redditClone.model.entity.Community;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModeratorService moderatorService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private DTOService dtoService;


    @GetMapping(value = "/{id}")
    public ResponseEntity<CommunityDTO> findOne(@PathVariable int id){
        Community community = communityService.findById(id);
        return new ResponseEntity<>(new CommunityDTO(community), HttpStatus.OK);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDTO>> findAllPostsForCommunity(@PathVariable int id){

        Community community = communityService.findById(id);
        List<PostDTO> returnPost = dtoService.postToDTO(community.getPosts());

        return new ResponseEntity<>(returnPost , HttpStatus.OK);

    }

    @GetMapping()
    public ResponseEntity<List<CommunityDTO>> findAll(){

        List<Community> communities = communityService.findAll();
        List<CommunityDTO> returnCommunities = new ArrayList<>();

        for (Community community: communities) {
            returnCommunities.add(new CommunityDTO(community));
        }

        return new ResponseEntity<>(returnCommunities, HttpStatus.OK);
    }

    //	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<CommunityDTO> createCommunity(@RequestBody CommunityDTO communityDTO /*, @RequestHeader("Authorization") String bearer*/, @RequestHeader("Token") String bearer) {

        String token = bearer.substring(7);
        String username = tokenUtils.getUsernameFromToken(token);
        Community community = new Community(communityDTO);
        Moderator moderator = new Moderator(0,userService.findByUsername(username), community);
        communityService.save(community);
        moderatorService.save(moderator);

        return new ResponseEntity<>(new CommunityDTO(community), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", value = "/{id}")
    public ResponseEntity<CommunityDTO> updateCommunity(@RequestBody CommunityDTO communityDTO, @PathVariable int id) {

        if (id == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Community community = communityService.findById(id);

            if (community.getName() != null) {
                community.setName(communityDTO.getName());
            }

            if (community.getDescription() != null) {
                community.setDescription(communityDTO.getDescription());
            }
            if (community.getSuspendedReason() != null) {
                community.setSuspendedReason(communityDTO.getSuspendedReason());
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
