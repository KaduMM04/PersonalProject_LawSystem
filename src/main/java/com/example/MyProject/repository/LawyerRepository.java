package com.example.MyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MyProject.model.Lawyer;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long>{
}
