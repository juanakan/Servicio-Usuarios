package com.users.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.users.model.User;
import com.users.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	public User registerUser(User user) {
		return userRepository.save(user);
	}
	
	public Optional<User> getUserByUsername (String username){
		return userRepository.findByUsername(username);
	}
	
	public Iterable<User> getUserAll (){
		return userRepository.findAll();
	}

	public boolean checkCredentials(String username, String password) {
	    Optional<User> user = userRepository.findByUsername(username);
	    return user.isPresent() && user.get().getPassword().equals(password);
	}

}
