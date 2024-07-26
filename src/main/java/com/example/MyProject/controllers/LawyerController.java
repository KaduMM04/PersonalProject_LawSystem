package com.example.MyProject.controllers;

import java.util.List;

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

import com.example.MyProject.dtos.LawyerDTO;
import com.example.MyProject.model.Lawyer;
import com.example.MyProject.services.LawyerService;

@RestController
@RequestMapping("/lawyer")
public class LawyerController {
	
	@Autowired
	private LawyerService lawyerService;
	
	@PostMapping
	public ResponseEntity<Lawyer> createLawyer(@RequestBody Lawyer lawyer) {
		lawyer  = lawyerService.saveLawyer(lawyer);
		 if (lawyer != null) {
	            return new ResponseEntity<>(lawyer, HttpStatus.CREATED);
	        } else {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
		LawyerDTO lawyerDTO = lawyerService.convertToDTO(lawyer);
		return ResponseEntity.ok().body(lawyerDTO);
	}
	
	@PutMapping(value = "/{oab}")
	public ResponseEntity<Lawyer> updateLawyer(@PathVariable Long oab, @RequestBody Lawyer lawyer) {
		lawyer = lawyerService.updateLawyer(oab, lawyer);
		return ResponseEntity.ok().body(lawyer);
	}
	
	@DeleteMapping(value = "/{oab}")
	public ResponseEntity<Void> deleteLawyer(@PathVariable Long oab) {
		lawyerService.deleteLawyerById(oab);
		return ResponseEntity.noContent().build();
	}
}
