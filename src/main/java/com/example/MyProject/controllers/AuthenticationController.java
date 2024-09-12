package com.example.MyProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyProject.dtos.AuthenticationDTO;
import com.example.MyProject.dtos.LoginResponseDTO;
import com.example.MyProject.dtos.RegisterDTO;
import com.example.MyProject.infra.security.TokenService;
import com.example.MyProject.model.UserLogin;
import com.example.MyProject.model.UserRole;
import com.example.MyProject.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        
        // Determine user type for token generation (if needed)
        String token = tokenService.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
	    if (this.repository.findByEmail(data.getEmail()) != null) {
	        return ResponseEntity.badRequest().build();
	    }
	    
	    String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
	    
	    UserLogin newUserLogin;
	    if (data.getRole() == UserRole.USERCLIENT) {
	        newUserLogin = new UserLogin(data.getEmail(), encryptedPassword, UserRole.USERCLIENT);
	    } else if (data.getRole() == UserRole.USERLAWYER) {
	        newUserLogin = new UserLogin(data.getEmail(), encryptedPassword, UserRole.USERLAWYER);
	    } else {
	        return ResponseEntity.badRequest().body("Invalid role");
	    }
	    
	    this.repository.save(newUserLogin);
	    return ResponseEntity.ok().build();
	}
	
	
	
}
