package com.example.MyProject.test;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.MyProject.model.Case;
import com.example.MyProject.model.Client;
import com.example.MyProject.model.Lawyer;
import com.example.MyProject.repository.CaseRepository;
import com.example.MyProject.repository.ClientRepository;
import com.example.MyProject.repository.LawyerRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private LawyerRepository lawyerRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CaseRepository caseRepository;
	
	@Override
	public void run(String...args) throws Exception {
		
		Lawyer l1 = new Lawyer("Dirlei", "dirlei@email", "123", 2444L, "civil");
		Lawyer l2 = new Lawyer("Priscilla", "priscilla@email", "123", 3214L, "previdenciario");
		
		lawyerRepository.saveAll(Arrays.asList(l1, l2));
		
		Client cl1 = new Client("Jorge", "jorge@email", "321", 33333L, "81280330");
		Client cl2 = new Client("Maria", "maria@email", "321", 99999L, "84296030");
		Client cl3 = new Client("Jubileu", "jubileu@email", "321", 555555L, "84111111");
		
		clientRepository.saveAll(Arrays.asList(cl1, cl2, cl3));
		
		Case c1 = new Case(1L, "civil", cl1, l1, "sla", 2000.0);
		Case c2 = new Case(2L, "previdenciario", cl3, l2, "sla", 4000.0);
		Case c3= new Case(3L, "trabalhista", cl2, null, "sla", 1000.0);
		
		caseRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		l1.getCases().add(c1);
		l2.getCases().add(c2);
		
		cl1.getCases().add(c1);
		cl2.getCases().add(c3);
		cl3.getCases().add(c2);
		
		
		lawyerRepository.saveAll(Arrays.asList(l1, l2));
		clientRepository.saveAll(Arrays.asList(cl1, cl2,cl3));
		
	}
}
