package com.ftn.redditClone.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.service.UserService;

@Component
public class WebSecurity {

	@Autowired
    private UserService userService;

    public boolean checkUserId(Authentication authentication, HttpServletRequest request, int id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        if(id == user.getId()) {
            return true;
        }
        return false;
    }
}
