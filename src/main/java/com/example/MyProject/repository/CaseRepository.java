package com.example.MyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MyProject.model.Case;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {
}
