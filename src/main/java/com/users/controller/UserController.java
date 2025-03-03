package com.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.users.dto.LoginRequest;
import com.users.dto.UserDto;
import com.users.model.User;
import com.users.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "API para gestionar usuarios")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/register")
	@Operation(summary = "Crear un usuario", description = "Registra un nuevo usuario si no existe")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		 try {
		        User nuevoUser = userService.registerUser(user);
		        UserDto userDTO = new UserDto(nuevoUser.getId(), nuevoUser.getUsername(), nuevoUser.getEmail());
		        return ResponseEntity.ok(userDTO);
		    } catch (IllegalArgumentException e) {
		        return ResponseEntity.badRequest().body(e.getMessage());
		    }
		}
	 
	 @GetMapping("/{username}")
	 @Operation(summary = "Obtener un usuario", description = "Obtiene un usuario por su username")
	 public UserDto getUserByUsername(@PathVariable String username) {
		 return userService.getUserByUsername(username) .orElseThrow(() -> new RuntimeException("Task not found"));
	    }
    
    @PostMapping("/login")
    @Operation(summary = "Loguear usuario", description = "Loguearse por usuario y contraseña")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean valid = userService.checkCredentials(loginRequest.getUsername(), loginRequest.getPassword());

        if (valid) {
            UserDto user = getUserByUsername(loginRequest.getUsername());
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(401).body("Invalid credentials"); 
    }
    
    @GetMapping("/")
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de usuarios sin contraseñas")
    public Iterable<UserDto> getAll(){
    	return userService.getUserAll();
    }
    
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario por su ID")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/modify/{id}")
    @Operation(summary = "Actualizar un usuario", description = "Modifica los datos de un usuario existente")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            UserDto userDTO = new UserDto(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail());
            return ResponseEntity.ok(userDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
