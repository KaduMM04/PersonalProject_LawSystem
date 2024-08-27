package com.example.MyProject.dtos;

import com.example.MyProject.model.UserRole;

public class RegisterDTO {
	
	private String email;
	private String password;
	private UserRole Role;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserRole getRole() {
		
		return Role;
	}
	public void setRole(UserRole role) {
		Role = role;
	}
}
