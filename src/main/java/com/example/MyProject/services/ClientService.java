package com.example.MyProject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyProject.model.Client;
import com.example.MyProject.repository.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public Client saveClient(Client client) {
		return clientRepository.save(client);
	}
	
	public Client getClientById(Long cpf) {
		return clientRepository.findById(cpf).orElse(null);
	}
	
	public void deleteClientById(Long cpf) {
		clientRepository.deleteById(cpf);
	}
	
	public List<Client> getAllClient(){
		return clientRepository.findAll();
	}
}
