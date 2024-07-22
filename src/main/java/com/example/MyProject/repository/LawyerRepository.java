package com.example.MyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MyProject.model.Lawyer;

public interface LawyerRepository extends JpaRepository<Lawyer, Long>{
}
