package com.ftn.redditClone.controller;

import antlr.Token;
import com.ftn.redditClone.model.dto.ModeratorDTO;
import com.ftn.redditClone.model.entity.Moderator;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("moderator")
public class ModeratorContoller {

    @Autowired
    private ModeratorService moderatorService;

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ModeratorDTO> FindOne(@PathVariable int id, @RequestHeader("Authorization") String bearer){

//        Moderator moderator = moderatorService.findById(id);
        return new ResponseEntity<>(new ModeratorDTO(null), HttpStatus.OK);
    }

}
