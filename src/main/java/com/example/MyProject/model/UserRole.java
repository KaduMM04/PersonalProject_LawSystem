package com.example.MyProject.model;

public enum UserRole {
	
	ADMIN("admin"),
	
	USERLAWYER("lawyer"),
	
	USERCLIENT("client");
	
	private String role;
	
	UserRole(String role){
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
