package com.ftn.redditClone.controller;

import antlr.Token;
import com.ftn.redditClone.model.dto.ModeratorDTO;
import com.ftn.redditClone.model.entity.Community;
import com.ftn.redditClone.model.entity.Moderator;
import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.DTOService;
import com.ftn.redditClone.service.ModeratorService;
import com.ftn.redditClone.service.UserService;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<ModeratorDTO> FindOne(@PathVariable int id, @RequestHeader("Authorization") String bearer){

        Moderator moderator = moderatorService.findById(id).get();
        return new ResponseEntity<>(new ModeratorDTO(moderator), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ModeratorDTO>> findAll(){
        List<Moderator> moderators = moderatorService.findAll();
        List<ModeratorDTO> returnModerators = new ArrayList<>();
        for(Moderator moderator: moderators){
            if(!moderator.isDeleted())
                returnModerators.add(new ModeratorDTO(moderator));
        }
        return new ResponseEntity<>(returnModerators, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ModeratorDTO> saveModerator(@RequestBody ModeratorDTO moderatorDTO){
        Moderator moderator = new Moderator(moderatorDTO);
        moderator.setDeleted(false);
        moderatorService.save(moderator);
        return new ResponseEntity<>(new ModeratorDTO(moderator), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ModeratorDTO> deleteModerator(@PathVariable int id, @RequestBody ModeratorDTO moderatorDTO){
        Moderator moderator = new Moderator(moderatorDTO);
        if(moderator.getCommunity().getId() != id){

        }
        moderator.setDeleted(true);
        moderatorService.save(moderator);
        return new ResponseEntity<>(new ModeratorDTO(moderator), HttpStatus.OK);
    }

}
