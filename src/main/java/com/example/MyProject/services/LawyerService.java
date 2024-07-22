package com.example.MyProject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyProject.model.Lawyer;
import com.example.MyProject.repository.LawyerRepository;

@Service
public class LawyerService {
	
	@Autowired
	private LawyerRepository lawyerRepository;
	
	public Lawyer saveLawyer(Lawyer lawyer) {
		return lawyerRepository.save(lawyer);
	}
	
	public Lawyer getLawyerById(Long oab) {
		return lawyerRepository.findById(oab).orElse(null);
	}
	
	public void deleteLawyerById(Long oab) {
		lawyerRepository.deleteById(oab);
	}
	
	public List<Lawyer> getAllLawyer() {
		return lawyerRepository.findAll();
	}
}
