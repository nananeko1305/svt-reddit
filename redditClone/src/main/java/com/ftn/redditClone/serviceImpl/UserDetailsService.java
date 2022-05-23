package com.ftn.redditClone.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.repository.UserRepository;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.UserService;

@Primary
@Service
@Lazy
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenUtils	tokenUtils;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	public UserDetailsService(UserService userService){
	 this.userService = userService; }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("There is no user with username " + username);
		} else {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			String role = user.getRole().toString();
			grantedAuthorities.add(new SimpleGrantedAuthority(role));

			return new org.springframework.security.core.userdetails.User(user.getUsername().trim(),
					user.getPassword().trim(), grantedAuthorities);
		}
	}
}
