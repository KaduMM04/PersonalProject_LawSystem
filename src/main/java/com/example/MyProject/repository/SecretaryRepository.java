package com.example.MyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MyProject.model.Secretary;

public interface SecretaryRepository extends JpaRepository<Secretary, Integer>{
}
