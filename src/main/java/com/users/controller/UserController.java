package com.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.users.dto.LoginRequest;
import com.users.model.User;
import com.users.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;
	
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		return userService.registerUser(user);
	}
	 
	 @GetMapping("/{username}")
	    public User getUserByUsername(@PathVariable String username) {
	        return userService.getUserByUsername(username) .orElseThrow(() -> new RuntimeException("Task not found"));
	    }
    
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        boolean valid = userService.checkCredentials(loginRequest.getUsername(), loginRequest.getPassword());

        if (valid) {
            return "Login successful!";
        }

        return "Invalid credentials!";
    }
    
    @GetMapping("/")
    public Iterable<User> getAll(){
    	return userService.getUserAll();
    }

}
