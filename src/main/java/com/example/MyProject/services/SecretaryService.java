package com.example.MyProject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyProject.model.Secretary;
import com.example.MyProject.repository.SecretaryRepository;

@Service
public class SecretaryService {
	
	@Autowired
	private SecretaryRepository secretaryRepository;
	
	public Secretary saveSecretary(Secretary secretary) {
		return secretaryRepository.save(secretary);
	}
	
	public Secretary getSecretaryById(Integer numRegistration) {
		return secretaryRepository.findById(numRegistration).orElse(null);
	}
	
	public void deletedSecretaryById(Integer numRegistration) {
		secretaryRepository.deleteById(numRegistration);
	}
	
	public List<Secretary> getAllSecretary() {
		return secretaryRepository.findAll();
	}
}
