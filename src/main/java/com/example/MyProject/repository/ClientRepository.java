package com.example.MyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MyProject.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
}
