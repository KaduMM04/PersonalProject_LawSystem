package com.example.MyProject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyProject.dtos.CaseDTO;
import com.example.MyProject.dtos.ClientBasicInfoDTO;
import com.example.MyProject.dtos.LawyerBasicInfoDTO;
import com.example.MyProject.model.Case;
import com.example.MyProject.model.Client;
import com.example.MyProject.model.Lawyer;
import com.example.MyProject.repository.CaseRepository;
import com.example.MyProject.repository.ClientRepository;
import com.example.MyProject.repository.LawyerRepository;

@Service
public class CaseService {
	
	@Autowired
	private CaseRepository caseRepository;
	
	@Autowired
	private LawyerRepository lawyerRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	public Case saveCase(Case c) {
		 if (c.getClient() == null) {
	            throw new IllegalArgumentException("Um cliente deve ser associado ao caso.");
	        }
		 
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
	
	public Case updateCase(Long id, Case updatedCase) {
		Case existingCase = caseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Case not found with ID: " + id));

		existingCase.setTypeCase(updatedCase.getTypeCase());
	    existingCase.setDescription(updatedCase.getDescription());
	    existingCase.setPrice(updatedCase.getPrice());

	    // Associa o advogado ao caso
        if (updatedCase.getLawyer() != null && updatedCase.getLawyer().getOab() != null) {
            Lawyer lawyer = lawyerRepository.findById(updatedCase.getLawyer().getOab())
                .orElseThrow(() -> new RuntimeException("Lawyer not found with OAB: " + updatedCase.getLawyer().getOab()));
            existingCase.setLawyer(lawyer);
        }

        // Associa o client ao caso
        if (updatedCase.getClient() != null && updatedCase.getClient().getCpf() != null) {
            Client client = clientRepository.findById(updatedCase.getClient().getCpf())
                .orElseThrow(() -> new RuntimeException("Lawyer not found with OAB: " + updatedCase.getLawyer().getOab()));
            existingCase.setClient(client);
        }


        return caseRepository.save(existingCase);
    }
	
	public CaseDTO convertToDTO(Case c) {
		LawyerBasicInfoDTO lawyerInfo = null;
		
	    if (c.getLawyer() != null) {
	        lawyerInfo = new LawyerBasicInfoDTO(c.getLawyer().getOab(), c.getLawyer().getName());
	    } else {
	        // Define valores padrão se o advogado não estiver associado
	        lawyerInfo = new LawyerBasicInfoDTO(0000L, "Não associado");
	    }
	    
	    ClientBasicInfoDTO clientInfo;
	    if (c.getClient() != null) {
	        clientInfo = new ClientBasicInfoDTO(c.getClient().getCpf(), c.getClient().getName());
	    } else {
	        // Isso não deve ocorrer se a validação de criação estiver funcionando corretamente
	        clientInfo = new ClientBasicInfoDTO(0000L, "Cliente não associado");
	    }
	    
	    return new CaseDTO(c.getId(), c.getTypeCase(), c.getDescription(), c.getPrice(), lawyerInfo, clientInfo);
	     
	}
}
