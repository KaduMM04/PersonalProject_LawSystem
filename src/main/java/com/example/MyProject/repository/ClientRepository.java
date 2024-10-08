package com.example.MyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MyProject.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	 Client findByEmail(String email);

}
