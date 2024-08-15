package com.example.MyProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.MyProject.dtos.LawyerDTO;
import com.example.MyProject.model.Lawyer;
import com.example.MyProject.services.CaseService;
import com.example.MyProject.services.LawyerService;

@RestController
@RequestMapping("/lawyer")
public class LawyerController {
	
	@Autowired
	private LawyerService lawyerService;
	
	@Autowired
	private CaseService caseService;
	
	@PostMapping
	public ResponseEntity<Lawyer> createLawyer(@RequestBody Lawyer lawyer) {
		try {
			lawyer  = lawyerService.saveLawyer(lawyer);
			return ResponseEntity.ok().body(lawyer);
		} catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(null);
		}
		
	}
	
	@GetMapping
	public ResponseEntity<List<LawyerDTO>> findAll() {
		List<Lawyer> list = lawyerService.getAllLawyer();
		List<LawyerDTO> listDTOs = list.stream()
				.map(lawyerService ::convertToDTO)
				.toList();
		return ResponseEntity.ok().body(listDTOs);
	}
	
	@GetMapping(value = "/{oab}")
	public ResponseEntity<LawyerDTO> findById(@PathVariable Long oab) {
	    Lawyer lawyer = lawyerService.getLawyerById(oab);
	    if (lawyer == null) {
	        return ResponseEntity.notFound().build();
	    }
	    LawyerDTO lawyerDTO = lawyerService.convertToDTO(lawyer);
	    return ResponseEntity.ok().body(lawyerDTO);
	}
	
	@GetMapping("/{oab}/cases")
	public ResponseEntity<List<CaseDTO>> findCasesByLawyerId(@PathVariable Long oab) {
	    List<CaseDTO> caseDTOs = caseService.getCasesByLawyerId(oab);
	    return ResponseEntity.ok().body(caseDTOs);
	}

	@PutMapping(value = "/{oab}")
	public ResponseEntity<LawyerDTO> updateLawyer(@PathVariable Long oab, @RequestBody LawyerDTO dto) {
		Lawyer lawyer = lawyerService.updateLawyer(oab, dto);
		if (lawyer == null) {
	        return ResponseEntity.notFound().build();
	    }
		LawyerDTO lawyerDTO = lawyerService.convertToDTO(lawyer);
		return ResponseEntity.ok().body(lawyerDTO);
	}
	
	@DeleteMapping(value = "/{oab}")
	public ResponseEntity<Void> deleteLawyer(@PathVariable Long oab) {
		lawyerService.deleteLawyerById(oab);
		return ResponseEntity.noContent().build();
	}
}
