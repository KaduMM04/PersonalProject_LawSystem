package com.example.MyProject.services;

import java.util.List;
import java.util.Optional;
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
		if (lawyerRepository.existsById(lawyer.getOab())) {
			throw new RuntimeException("Lawyer with OAB " + lawyer.getOab());
		}
			return lawyerRepository.save(lawyer);
		
	}
	
	public Lawyer getLawyerById(Long oab) {
	        Optional<Lawyer> optionalLawyer = lawyerRepository.findById(oab);
	        if (optionalLawyer.isPresent()) {
	            return optionalLawyer.get();
	        } else {
	            System.out.println("Lawyer not found with oab: " + oab);
	            throw new RuntimeException("Lawyer not found with oab: " + oab);
	        }
	}
	
	public Lawyer getLawyerByEmail(String email) {
        Lawyer optionalLawyer = lawyerRepository.findByEmail(email);
        if (optionalLawyer == null) {
            throw new RuntimeException("Lawyer not found with email: " + email);
        }
        return optionalLawyer;
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
	
	public Lawyer updateLawyer(Long oab, LawyerDTO dto) {
        Lawyer existingLawyer = lawyerRepository.findById(oab)
            .orElseThrow(() -> new RuntimeException("Lawyer not found with OAB: " + oab));

        existingLawyer.setName(dto.getName());
        existingLawyer.setEmail(dto.getEmail());
        existingLawyer.setPassword(dto.getPassword());
        existingLawyer.setPraticeArea(dto.getPraticeArea());

        return lawyerRepository.save(existingLawyer);
    }
	
	public LawyerDTO convertToDTO(Lawyer lawyer) {
	    if (lawyer == null) {
	        throw new IllegalArgumentException("Lawyer cannot be null");
	    }

	    List<Long> caseIds = lawyer.getCases().stream()
	        .map(Case::getId)
	        .collect(Collectors.toList());
	    return new LawyerDTO(lawyer.getName(), lawyer.getEmail(), lawyer.getPassword(), lawyer.getOab(), lawyer.getPraticeArea(), caseIds);
	}
}
