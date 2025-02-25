package com.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.users.dto.LoginRequest;
import com.users.dto.UserDto;
import com.users.model.User;
import com.users.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		 try {
		        User savedUser = userService.registerUser(user);
		        UserDto userDTO = new UserDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
		        return ResponseEntity.ok(userDTO);
		    } catch (IllegalArgumentException e) {
		        return ResponseEntity.badRequest().body(e.getMessage());
		    }
		}
	 
	 @GetMapping("/{username}")
	 public UserDto getUserByUsername(@PathVariable String username) {
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
    public Iterable<UserDto> getAll(){
    	return userService.getUserAll();
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
