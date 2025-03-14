package com.users.service;

import java.util.Optional;

import com.users.dto.UserDto;
import com.users.model.User;


public interface UserService {	
	public User registerUser(User user);
	
	public Optional<UserDto> getUserByUsername (String username);
	
	public Iterable<UserDto> getUserAll ();

	public boolean checkCredentials(String username, String password);
	
	public void deleteUser (Long id);
	
	public User updateUser(Long id, User user);

	public Optional<User> findById(Long id);

}
