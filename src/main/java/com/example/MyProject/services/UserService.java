package com.example.MyProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.MyProject.model.Client;
import com.example.MyProject.model.Lawyer;
import com.example.MyProject.repository.ClientRepository;
import com.example.MyProject.repository.LawyerRepository;

@Service
public class UserService implements UserDetailsService {
	
	 @Autowired
	    private ClientRepository clientRepository;

	    @Autowired
	    private LawyerRepository lawyerRepository;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Client client = clientRepository.findByEmail(username);
	        if (client != null) {
	            return client;
	        }

	        Lawyer lawyer = lawyerRepository.findByEmail(username);
	        if (lawyer != null) {
	            return lawyer;
	        }

	        throw new UsernameNotFoundException("User not found with username: " + username);
	    }

}
