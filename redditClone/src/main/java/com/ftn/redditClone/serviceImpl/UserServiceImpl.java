package com.ftn.redditClone.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.repository.UserRepository;

@Service
public class UserServiceImpl {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public void deleteById(Integer id) {
		userRepository.deleteById(id);
	}
	
	public User findById(Integer id) {
		return userRepository.findById(id).orElseGet(null);
	}
	
}
