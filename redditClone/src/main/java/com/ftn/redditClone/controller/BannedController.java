package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.BannedDTO;
import com.ftn.redditClone.model.entity.Banned;
import com.ftn.redditClone.model.entity.Community;
import com.ftn.redditClone.model.entity.Moderator;
import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.service.BannedService;
import com.ftn.redditClone.service.CommunityService;
import com.ftn.redditClone.service.ModeratorService;
import com.ftn.redditClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("banneds")
public class BannedController {

    @Autowired
    private BannedService bannedService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private ModeratorService moderatorService;

    @PostMapping()
    public ResponseEntity<BannedDTO> saveBan(@RequestBody BannedDTO bannedDTO){
        Banned banned = new Banned(bannedDTO);
        User user = userService.findById(bannedDTO.getUser().getId());
        banned.setUser(user);
        Community community = communityService.findById(bannedDTO.getCommunity().getId());
        banned.setCommunity(community);
        Moderator moderator = moderatorService.findById(bannedDTO.getModerator().getId()).get();
        banned.setModerator(moderator);
        bannedService.save(banned);
        return new ResponseEntity<>(new BannedDTO(banned), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BannedDTO> deleteBan(@PathVariable int id){
        bannedService.delete(id);
        return new ResponseEntity<>(new BannedDTO(), HttpStatus.OK);
    }

}
