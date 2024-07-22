package com.example.MyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MyProject.model.Case;

public interface CaseRepository extends JpaRepository<Case, Long>{
}
