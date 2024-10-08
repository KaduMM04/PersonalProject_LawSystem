package com.example.MyProject.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String email;
	private String password;
	private UserRole role;
	
	public User () {
	}
	
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public User(String email, String password, UserRole role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	public User(String name, String email, String password, UserRole role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    if (this.role == UserRole.ADMIN) {
	        return List.of(
	            new SimpleGrantedAuthority("ROLE_ADMIN"),
	            new SimpleGrantedAuthority("ROLE_USER")
	        );
	    } else if (this.role == UserRole.USERLAWYER) {
	        return List.of(new SimpleGrantedAuthority("ROLE_USERLAWYER"));
	    } else if (this.role == UserRole.USERCLIENT) {
	        return List.of(new SimpleGrantedAuthority("ROLE_USERCLIENT"));
	    } else {
	        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	    }
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
	    return true; // Adjust based on your needs
	}

	@Override
	public boolean isAccountNonLocked() {
	    return true; // Adjust based on your needs
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return true; // Adjust based on your needs
	}

	@Override
	public boolean isEnabled() {
	    return true; // Adjust based on your needs
	}

}
