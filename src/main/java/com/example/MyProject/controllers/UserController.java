package com.example.MyProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyProject.model.UserLogin;
import com.example.MyProject.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserLogin>> getUsers() {
		List<UserLogin> list = userService.getAllUsers();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<UserLogin> getUserByEmail(@PathVariable String email) {
		UserLogin userClient = userService.getUserByEmail(email);
		return ResponseEntity.ok().body(userClient);
	}

}
