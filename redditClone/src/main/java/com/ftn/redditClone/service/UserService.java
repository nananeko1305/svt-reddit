package com.ftn.redditClone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.redditClone.model.entity.User;
import com.ftn.redditClone.repository.UserRepository;

public interface UserService {
	
	public List<User> findAll();
	
	public void save(User user);
	
	public void deleteById(int id);
	
	public User findById(int id);
	
	public void changePassword(String username, String newPassword);
	
	public User findByUsername(String username);

	public void updateUser(User user);
}
