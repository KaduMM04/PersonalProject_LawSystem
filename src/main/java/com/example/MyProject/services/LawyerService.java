package com.example.MyProject.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyProject.dtos.LawyerDTO;
import com.example.MyProject.model.Case;
import com.example.MyProject.model.Lawyer;
import com.example.MyProject.repository.CaseRepository;
import com.example.MyProject.repository.LawyerRepository;

@Service
public class LawyerService {
	
	@Autowired
	private LawyerRepository lawyerRepository;
	
	@Autowired
	private CaseRepository caseRepository;
	
	public Lawyer saveLawyer(Lawyer lawyer) {
		if (getLawyerById(lawyer.getOab()) != null) {
			return null;
		} else {
			return lawyerRepository.save(lawyer);
		}
		
	}
	
	public Lawyer getLawyerById(Long oab) {
		return lawyerRepository.findById(oab).orElse(null);
	}
	
	public void deleteLawyerById(Long oab) {
		Lawyer lawyer = lawyerRepository.findById(oab)
				.orElseThrow(() -> new RuntimeException("Lawyer not found with OAB: " + oab));
	
		 // Desassociando casos
		for (Case c : lawyer.getCases()) {
            c.setLawyer(null); 
            caseRepository.save(c); // Salva o caso atualizado
        }

        // Removendo advogado
        lawyerRepository.delete(lawyer);
    
	}
	
	public List<Lawyer> getAllLawyer() {
		return lawyerRepository.findAll();
	}
	
	public Lawyer updateLawyer(Long oab, Lawyer updatedLawyer) {
        Lawyer existingLawyer = lawyerRepository.findById(oab)
            .orElseThrow(() -> new RuntimeException("Lawyer not found with OAB: " + oab));

        existingLawyer.setName(updatedLawyer.getName());
        existingLawyer.setEmail(updatedLawyer.getEmail());
        existingLawyer.setPassword(updatedLawyer.getPassword());
        existingLawyer.setPraticeArea(updatedLawyer.getPraticeArea());

        return lawyerRepository.save(existingLawyer);
    }
	
	public LawyerDTO convertToDTO(Lawyer lawyer) {
	    List<Long> caseIds = lawyer.getCases().stream()
	        .map(Case::getId)
	        .collect(Collectors.toList());
	    return new LawyerDTO(lawyer.getName(), lawyer.getEmail(), lawyer.getOab(), lawyer.getPraticeArea(), caseIds);
	}
}
