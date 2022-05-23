package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.PasswordChangeDTO;
import com.ftn.redditClone.model.dto.UserDTO;
import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.repository.UserRepository;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService, TokenUtils tokenUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping()
    public void findOne(@RequestBody UserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        System.out.println(user.toString());
    }

    @GetMapping(value = "profile")
    public ResponseEntity<UserDTO> findOne(@RequestParam String username) {

        User user = userService.findByUsername(username);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {

        User user = new User(userDTO.getUsername(), userDTO.getRole(), userDTO.getPassword(), userDTO.getEmail(),
                userDTO.getAvatar(), userDTO.getRegistrationDate(), userDTO.getDescription(), userDTO.getDisplayName());

        userService.save(user);
        return new ResponseEntity<>(new UserDTO(userDTO.getUsername(), userDTO.getRole(), userDTO.getPassword(),
                userDTO.getEmail(), userDTO.getAvatar(), userDTO.getRegistrationDate(), userDTO.getDescription(),
                userDTO.getDisplayName()), HttpStatus.CREATED);

    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<String> login(@RequestBody UserDTO userDto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDto.getUsername(), userDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
            return ResponseEntity.ok(tokenUtils.generateToken(userDetails));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> deleteUser(@RequestBody UserDTO userDTO) {

        userService.deleteById(userDTO.getId());
        return new ResponseEntity<>(HttpStatus.GONE);

    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

        userService.deleteById(userDTO.getId());
        return new ResponseEntity<>(HttpStatus.GONE);

    }

    //	@PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping(value = "/passwordChange", consumes = "application/json")
    public ResponseEntity<PasswordChangeDTO> updateUserPassword(@RequestBody PasswordChangeDTO passwordChangeDTO,
                                                                @RequestHeader("Authorization") String token) {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	
        System.out.println("TU SAM!");
        System.out.println(token);
        System.out.println(token.substring(7));
        String username = tokenUtils.getUsernameFromToken(token.substring(7));
        
        User user = userService.findByUsername(username); 
        user.setPassword(encoder.encode(passwordChangeDTO.getNewPassword()));
        userService.save(user);
        
        return new ResponseEntity<>(new PasswordChangeDTO(passwordChangeDTO.getUsername(),
                passwordChangeDTO.getOldPassword(), passwordChangeDTO.getNewPassword()), HttpStatus.GONE);

    }

}
