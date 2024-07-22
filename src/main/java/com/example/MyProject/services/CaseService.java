package com.example.MyProject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyProject.model.Case;
import com.example.MyProject.repository.CaseRepository;

@Service
public class CaseService {
	
	@Autowired
	private CaseRepository caseRepository;
	
	public Case saveCase(Case c) {
		return caseRepository.save(c);
	}
	
	public Case getCaseById(Long id) {
		return caseRepository.findById(id).orElse(null);
	}
	
	public void deleteCaseById(Long id) {
		caseRepository.deleteById(id);
	}
	
	public List<Case> getAllListCase() {
		return caseRepository.findAll();
	}
}
