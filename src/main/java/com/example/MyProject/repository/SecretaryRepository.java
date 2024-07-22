package com.example.MyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MyProject.model.Secretary;

@Repository
public interface SecretaryRepository extends JpaRepository<Secretary, Integer>{
}
