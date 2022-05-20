package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.UserDTO;
import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.repository.UserRepository;
import com.ftn.redditClone.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserService userService;
	
    @GetMapping()
    public void Login(@RequestParam String username){

    }
    
    @PostMapping(value = "registration", consumes = "application/json")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO){
    	
    	User user = new User(userDTO.getId(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getAvatar(), userDTO.getRegistrationDate(), userDTO.getDescription(), userDTO.getDisplayName());
    	
    	userService.save(user);
    	return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    	
    }

}
