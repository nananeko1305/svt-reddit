package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.ModeratorDTO;
import com.ftn.redditClone.model.dto.PasswordChangeDTO;
import com.ftn.redditClone.model.dto.PostDTO;
import com.ftn.redditClone.model.dto.UserDTO;
import com.ftn.redditClone.model.entity.*;
import com.ftn.redditClone.repository.UserRepository;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.CommunityService;
import com.ftn.redditClone.service.DTOService;
import com.ftn.redditClone.service.ModeratorService;
import com.ftn.redditClone.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.usertype.UserType;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
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
    private DTOService dtoService;

    @Autowired
    private ModeratorService moderatorService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService, TokenUtils tokenUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> findAll() {

        List<User> users = userService.findAll();
        List<UserDTO> returnUsers = new ArrayList<>();
        for (User user :users) {
            returnUsers.add(new UserDTO(user));
        }

        return new ResponseEntity<>(returnUsers, HttpStatus.OK);
    }

    @GetMapping("{id}/community/{communityId}")
    public ResponseEntity<ModeratorDTO> returnModeratoor(@PathVariable int id, @PathVariable int communityId){
        Moderator moderator = moderatorService.findByUserId(id,communityId);
        if(moderator == null){
            Moderator newModerator = new Moderator(0,userService.findById(id),communityService.findById(communityId), false);
            moderatorService.save(newModerator);
            return new ResponseEntity<>(new ModeratorDTO(newModerator), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ModeratorDTO(moderator), HttpStatus.OK);
        }

    }

    @GetMapping(value = "{id}/karma")
    public ResponseEntity<Integer> returnKarma(@PathVariable int id){

        int karma = 0;
        User user = userService.findById(id);
        for (Post post : user.getPosts()){
            for (Reaction reaction: post.getReactions()){
                if(reaction.getReactionType() == ReactionType.UPVOTE){
                    karma++;
                }else {
                    karma--;
                }
            }
        }
        for (Comment comment : user.getComments()){
            for (Reaction reaction: comment.getReactions()){
                if(reaction.getReactionType() == ReactionType.UPVOTE){
                    karma++;
                }else {
                    karma--;
                }
            }
        }
        return new ResponseEntity<>(karma, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findOne(@PathVariable int id, @RequestHeader(value = "Authorization", required = false) String bearer) {

        if(!bearer.equals("Bearer")){
            if(!bearer.substring(7).equals("")){
                String token = bearer.substring(7);
                System.out.println(token);
                String username = tokenUtils.getUsernameFromToken(token);
                User user = userService.findByUsername(username);
                return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(new UserDTO(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {

        User user = new User(userDTO);
        user.setRegistrationDate(LocalDate.now());
        user.setRole(Role.USER);
        userService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);

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

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable int id) {

        User user = userService.findById(id);

        if (userDTO.getDescription() != null) {
            user.setDescription(userDTO.getDescription());
        }

        if (userDTO.getAvatar() != null) {
            user.setAvatar(userDTO.getAvatar());
        }

        if (userDTO.getDisplayName() != null) {
            user.setDisplayName(userDTO.getDisplayName());
        }
        userService.updateUser(user);

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);

    }

    //	@PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping(value = "{id}/passwordChange", consumes = "application/json")
    public ResponseEntity<PasswordChangeDTO> updateUserPassword(@RequestBody PasswordChangeDTO passwordChangeDTO,
                                                                @RequestHeader("Authorization") String bearer, @PathVariable int id) {
        User user = userService.findById(id);
        if(user.getId() == id){
            if (passwordChangeDTO.getOldPassword() == null || passwordChangeDTO.getNewPassword() == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
                if (bcpe.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
                    userService.changePassword(user.getUsername(), passwordChangeDTO.getNewPassword());
                }else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(new PasswordChangeDTO(passwordChangeDTO.getOldPassword(), passwordChangeDTO.getNewPassword(), passwordChangeDTO.getUser()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
