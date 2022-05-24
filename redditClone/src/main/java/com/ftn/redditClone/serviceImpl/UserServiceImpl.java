package com.ftn.redditClone.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.repository.UserRepository;
import com.ftn.redditClone.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	PasswordEncoder passwordEncoder;
	
	@Autowired
    @Lazy
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()) );
		userRepository.save(user);
	}
	
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}
	
	public User findById(int id) {
		return userRepository.findById(id).orElseGet(null);
	}

	@Override
	public void changePassword(String username, String newPassword) {

		User user = findByUsername(username);
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
}
