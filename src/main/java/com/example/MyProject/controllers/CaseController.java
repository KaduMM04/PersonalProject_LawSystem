package com.example.MyProject.controllers;

import java.util.List;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyProject.dtos.CaseDTO;
import com.example.MyProject.model.Case;
import com.example.MyProject.services.CaseService;

@RestController
@RequestMapping("/case")
public class CaseController {
	
	@Autowired
	private CaseService caseService;
	
	@PostMapping
	public ResponseEntity<CaseDTO> createCase(@RequestBody Case c) {
		
		try {
	        // Valide se o cliente está associado
	        if (c.getClient() == null) {
	            return ResponseEntity.badRequest().body(null);
	        }
		
	        c = caseService.saveCase(c) ;
			
			Case createdCase = caseService.saveCase(c);
			CaseDTO caseDTO = caseService.convertToDTO(createdCase);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(caseDTO);
		 
		} catch (IllegalArgumentException e) {
	        // Retorne uma resposta de erro com status 400 Bad Request
	        return ResponseEntity.badRequest().body(null);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	@GetMapping
	public ResponseEntity<List<CaseDTO>> findAll() {
		List<Case> list = caseService.getAllListCase();
		List<CaseDTO> listDTOs = list.stream()
				.map(caseService::convertToDTO)
				.toList();
		return ResponseEntity.ok().body(listDTOs);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CaseDTO> findById(@PathVariable Long id) {
		Case c = caseService.getCaseById(id);
		CaseDTO caseDTO = caseService.convertToDTO(c);
		return ResponseEntity.ok().body(caseDTO);
	}
	
	@PutMapping(value = "/{id}")
	 public ResponseEntity<CaseDTO> updateCase(@PathVariable Long id, @RequestBody Case updatedCase) throws RoleNotFoundException {
		 try {
		        Case updated = caseService.updateCase(id, updatedCase);
		        return ResponseEntity.ok(caseService.convertToDTO(updated));
		    } catch (IllegalArgumentException e) {
		        return ResponseEntity.badRequest().body(null);
		    }
    }
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCase(@PathVariable Long id) {
		caseService.deleteCaseById(id);
		return ResponseEntity.noContent().build();
	}
}
