package com.example.MyProject.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.MyProject.dtos.ClientDTO;
import com.example.MyProject.model.Case;
import com.example.MyProject.model.Client;
import com.example.MyProject.model.UserRole;
import com.example.MyProject.repository.CaseRepository;
import com.example.MyProject.repository.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CaseRepository caseRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	public Client saveClient(Client client) {
		if (client.getRole() == null) {
			client.setRole(UserRole.USERCLIENT);
		}
		client.setPassword(passwordEncoder.encode(client.getPassword()));
		return clientRepository.save(client);
	}
	
	public Client getClientById(Long cpf) {
		return clientRepository.findById(cpf).orElse(null);
	}
	
	public void deleteClientById(Long cpf) {
		Client client = clientRepository.findById(cpf)
				.orElseThrow(() -> new RuntimeException("Client not found with Cpf: " + cpf));
		
		// Desassociando casos
		for (Case c : client.getCases()) {
			c.setClient(null);
			caseRepository.save(c);
		}
		
		clientRepository.delete(client);
		
	}
	
	public List<Client> getAllClient(){
		return clientRepository.findAll();
	}
	
	public Client updateLawyer(Long cpf, Client updatedClient) {
		Client existingClient = clientRepository.findById(cpf)
            .orElseThrow(() -> new RuntimeException("Client not found with Cpf: " + cpf));

		existingClient.setCpf(updatedClient.getCpf());
		existingClient.setName(updatedClient.getName());
		existingClient.setEmail(updatedClient.getEmail());
		existingClient.setPassword(passwordEncoder.encode(updatedClient.getPassword()));
		existingClient.setCep(updatedClient.getCep());
		
		if (existingClient.getRole() == null) {
	        existingClient.setRole(UserRole.USERCLIENT);
	    }

        return clientRepository.save(existingClient);
    }
	
	public ClientDTO convertToDTO(Client client) {
	    List<Long> caseIds = client.getCases().stream()
	        .map(Case::getId)
	        .collect(Collectors.toList());
	    return new ClientDTO(client.getCpf(), client.getName(), client.getEmail(), client.getCep(), caseIds);
	}
}
