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

import com.example.MyProject.dtos.ClientDTO;
import com.example.MyProject.model.Client;
import com.example.MyProject.services.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@PostMapping
	public ResponseEntity<Client> createClient(@RequestBody Client client) {
		client = clientService.saveClient(client);
		return new ResponseEntity<>(client, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ClientDTO>> findAll() {
		List<Client> list = clientService.getAllClient();
		List<ClientDTO> listDTOs = list.stream()
				.map(clientService::convertToDTO)
				.toList();
		return ResponseEntity.ok().body(listDTOs);
	}
	
	@GetMapping(value = "/{cpf}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long cpf) {
		Client client = clientService.getClientById(cpf);
		ClientDTO clientDTO = clientService.convertToDTO(client);
		return ResponseEntity.ok().body(clientDTO);
	}
	
	@PutMapping(value = "/{cpf}")
	public ResponseEntity<Client> updateClient(@PathVariable Long cpf, @RequestBody Client client) {
		client = clientService.updateLawyer(cpf, client);
		return ResponseEntity.ok().body(client);
	}
	
	@DeleteMapping(value = "/{cpf}")
	public ResponseEntity<Void> deleteClient(@PathVariable Long cpf) {
		clientService.deleteClientById(cpf);
		return ResponseEntity.noContent().build();
	}

}
