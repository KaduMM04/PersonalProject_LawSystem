package com.example.MyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.MyProject.model.UserLogin;

@Repository
public interface UserRepository extends JpaRepository<UserLogin, Long> {
	
	UserDetails findByEmail(String email);
	
}
