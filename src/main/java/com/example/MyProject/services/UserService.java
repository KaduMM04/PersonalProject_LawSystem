package com.example.MyProject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.MyProject.model.Client;
import com.example.MyProject.model.Lawyer;
import com.example.MyProject.model.UserLogin;
import com.example.MyProject.repository.ClientRepository;
import com.example.MyProject.repository.LawyerRepository;
import com.example.MyProject.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
		@Autowired
		 private UserRepository userRepository;
	
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
	    
	    public List<UserLogin> getAllUsers() {
	    	return userRepository.findAll();
	    }
	    
	    public UserLogin getUserByEmail(String email) {
	    	return userRepository.findByEmail(email);
	    }

}
