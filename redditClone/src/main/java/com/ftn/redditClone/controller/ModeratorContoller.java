package com.ftn.redditClone.controller;

import antlr.Token;
import com.ftn.redditClone.model.dto.ModeratorDTO;
import com.ftn.redditClone.model.entity.Community;
import com.ftn.redditClone.model.entity.Moderator;
import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("moderators")
public class ModeratorContoller {

    @Autowired
    private ModeratorService moderatorService;

    @Autowired
    private DTOService dtoService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private CommunityService communityService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ModeratorDTO> FindOne(@PathVariable int id, @RequestHeader("Authorization") String bearer) {

        Moderator moderator = moderatorService.findById(id).get();
        return new ResponseEntity<>(new ModeratorDTO(moderator), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ModeratorDTO>> findAll() {
        List<Moderator> moderators = moderatorService.findAll();
        List<ModeratorDTO> returnModerators = new ArrayList<>();
        for (Moderator moderator : moderators) {
            if (!moderator.isDeleted())
                returnModerators.add(new ModeratorDTO(moderator));
        }
        return new ResponseEntity<>(returnModerators, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ModeratorDTO> saveModerator(@RequestBody ModeratorDTO moderatorDTO) {

        boolean alreadyModerator = false;

        Community community = communityService.findById(moderatorDTO.getCommunity().getId());
        for (Moderator moderator : community.getModerators()) {
            if (moderator.getUser().getId() == moderatorDTO.getUser().getId() && !moderator.isDeleted()) {
                alreadyModerator = true;
                break;
            } else if (moderator.isDeleted()) {
                moderator.setDeleted(false);
                moderatorService.save(moderator);
                return new ResponseEntity<>(new ModeratorDTO(moderator), HttpStatus.OK);
            }
        }

        if (alreadyModerator) {

            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        } else {

            Moderator newModerator = new Moderator(moderatorDTO);
            newModerator.setDeleted(false);
            moderatorService.save(newModerator);

            return new ResponseEntity<>(new ModeratorDTO(newModerator), HttpStatus.OK);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<ModeratorDTO> deleteModerator(@PathVariable int id, @RequestBody ModeratorDTO moderatorDTO) {
        Community community = communityService.findById(moderatorDTO.getCommunity().getId());
        int numberOfModerators = 0;
        for (Moderator moderator : community.getModerators()) {
            if(!moderator.isDeleted()){
                numberOfModerators++;
            }
        }
        if (numberOfModerators == 1) {
            return new ResponseEntity<>(new ModeratorDTO(), HttpStatus.ACCEPTED);

        } else {
            Moderator moderator = new Moderator(moderatorDTO);
            moderator.setCommunity(communityService.findById(moderatorDTO.getCommunity().getId()));
            moderator.setDeleted(true);
            moderatorService.save(moderator);
            return new ResponseEntity<>(new ModeratorDTO(moderator), HttpStatus.OK);
        }
    }

}
