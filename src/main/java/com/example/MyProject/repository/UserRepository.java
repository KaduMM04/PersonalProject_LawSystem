package com.example.MyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MyProject.model.UserLogin;

@Repository
public interface UserRepository extends JpaRepository<UserLogin, Long> {
	
	UserLogin findByEmail(String email);
	
}
