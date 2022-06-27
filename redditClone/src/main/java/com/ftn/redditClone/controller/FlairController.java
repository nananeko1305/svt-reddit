package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.FlairDTO;
import com.ftn.redditClone.model.entity.Flair;
import com.ftn.redditClone.service.FlairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("flair")
public class FlairController {

    @Autowired
    private FlairService flairService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<FlairDTO> findOne(@PathVariable int id){
        Flair flair = flairService.findOne(id).get();
        return new ResponseEntity<>(new FlairDTO(flair), HttpStatus.OK);
    }


}
