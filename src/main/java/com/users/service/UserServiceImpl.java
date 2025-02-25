package com.users.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.users.dto.UserDto;
import com.users.model.User;
import com.users.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerUser(User user) {
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
	        throw new IllegalArgumentException("El usuario ya existe");
	    }
		return userRepository.save(user);
	}

	@Override
	public Optional<UserDto> getUserByUsername(String username) {
		 return userRepository.findByUsername(username)
	                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getEmail()));
	}

	@Override
	public Iterable<UserDto> getUserAll() {
		 return userRepository.findAll()
	                .stream()
	                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getEmail()))
	                .collect(Collectors.toList());
	}

	@Override
	public boolean checkCredentials(String username, String password) {
	    Optional<User> user = userRepository.findByUsername(username);
	    return user.isPresent() && user.get().getPassword().equals(password);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
	

}
