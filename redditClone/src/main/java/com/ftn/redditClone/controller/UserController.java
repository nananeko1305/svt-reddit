package com.ftn.redditClone.controller;

import com.ftn.redditClone.model.dto.PasswordChangeDTO;
import com.ftn.redditClone.model.dto.PostDTO;
import com.ftn.redditClone.model.dto.UserDTO;
import com.ftn.redditClone.model.entity.*;
import com.ftn.redditClone.repository.UserRepository;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.DTOService;
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
        List<UserDTO> returnUsers = new ArrayList<UserDTO>();
        for (User user :
                users) {
            returnUsers.add(new UserDTO(user.getId(), user.getUsername(), user.getRole(), user.getPassword(), user.getEmail(), user.getAvatar(), user.getRegistrationDate(), user.getDescription(), user.getDisplayName(), dtoService.postToDTO(user.getPosts()), dtoService.commentToDTO(user.getComments()), dtoService.reportToDTO(user.getReports()), dtoService.bannedToDTO(user.getBanneds())));
        }

        return new ResponseEntity<>(returnUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findOne(@PathVariable int id) {

        User user = userService.findById(id);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {

        User user = new User(userDTO.getId(), userDTO.getUsername(), Role.USER, userDTO.getPassword(), userDTO.getEmail(), userDTO.getAvatar(), LocalDate.now(), userDTO.getDescription(), userDTO.getDisplayName(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

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

        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }

        if (userDTO.getDescription() != null) {
            user.setDescription(userDTO.getDescription());
        }

        if (userDTO.getAvatar() != null) {
            user.setAvatar(userDTO.getAvatar());
        }

        if (userDTO.getDisplayName() != null) {
            user.setDisplayName(userDTO.getDisplayName());
        }

        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        userService.save(user);

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);

    }

    //	@PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping(value = "/passwordChange", consumes = "application/json")
    public ResponseEntity<PasswordChangeDTO> updateUserPassword(@RequestBody PasswordChangeDTO passwordChangeDTO,
                                                                @RequestHeader("Authorization") String token) {

        if (passwordChangeDTO.getOldPassword() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {

            String username = tokenUtils.getUsernameFromToken(token.substring(7));
            userService.changePassword(username, passwordChangeDTO.getNewPassword());

            return new ResponseEntity<>(new PasswordChangeDTO(passwordChangeDTO.getUsername(),
                    passwordChangeDTO.getOldPassword(), passwordChangeDTO.getNewPassword()), HttpStatus.GONE);
        }
    }

}
