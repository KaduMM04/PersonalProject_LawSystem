package com.example.MyProject.model;

public enum UserRole {
	
	ADMIN("admin"),
	
	USERLAWYER("lawyer"),
	
	USERSECRETARY("secretary"),
	
	USERCLIENT("client");
	
	private String role;
	
	UserRole(String role){
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
	public static UserRole fromString(String userRole) {
        for (UserRole role : UserRole.values()) {
            if (role.getRole().equalsIgnoreCase(userRole)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + userRole);
    }
}
