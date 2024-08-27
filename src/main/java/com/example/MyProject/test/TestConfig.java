package com.example.MyProject.test;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.MyProject.model.Case;
import com.example.MyProject.model.Client;
import com.example.MyProject.model.Lawyer;
import com.example.MyProject.model.UserLogin;
import com.example.MyProject.model.UserRole;
import com.example.MyProject.repository.CaseRepository;
import com.example.MyProject.repository.ClientRepository;
import com.example.MyProject.repository.LawyerRepository;
import com.example.MyProject.repository.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private LawyerRepository lawyerRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CaseRepository caseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void run(String...args) throws Exception {
		
		Lawyer l1 = new Lawyer("Dirlei", "dirlei@email", passwordEncoder.encode("123"), UserRole.USERLAWYER, 2444L, "Civil");
		Lawyer l2 = new Lawyer("Priscilla", "priscilla@email", passwordEncoder.encode("123"), UserRole.USERLAWYER, 3214L, "Previdenciario");
		
		
		lawyerRepository.saveAll(Arrays.asList(l1, l2));
		
		
		Client cl1 = new Client("Jorge", "jorge@email", passwordEncoder.encode("321"), UserRole.USERCLIENT, 33333L, "81280330");
		Client cl2 = new Client("Maria", "maria@email", passwordEncoder.encode("321"), UserRole.USERCLIENT, 99999L, "84296030");
		Client cl3 = new Client("Jubileu", "jubileu@email", passwordEncoder.encode("321"), UserRole.USERCLIENT, 555555L, "84111111");
		
		clientRepository.saveAll(Arrays.asList(cl1, cl2, cl3));
		
		Case c1 = new Case(1L, "Civil", cl1, l1, "sla", 2000.0);
		Case c2 = new Case(2L, "Previdenciario", cl3, l2, "sla", 4000.0);
		Case c3= new Case(3L, "Trabalhista", cl2, null, "sla", 1000.0);
		
		caseRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		l1.getCases().add(c1);
		l2.getCases().add(c2);
		
		cl1.getCases().add(c1);
		cl2.getCases().add(c3);
		cl3.getCases().add(c2);
		
		UserLogin u1 = new UserLogin(l1.getEmail(), l1.getPassword(), UserRole.USERLAWYER);
		UserLogin u2 = new UserLogin(l2.getEmail(), l2.getPassword(), UserRole.USERLAWYER);
		
		UserLogin u3 = new UserLogin(cl1.getEmail(), cl1.getPassword(), UserRole.USERCLIENT);
		UserLogin u4 = new UserLogin(cl2.getEmail(), cl2.getPassword(), UserRole.USERCLIENT);
		UserLogin u5 = new UserLogin(cl3.getEmail(), cl3.getPassword(), UserRole.USERCLIENT);
		
		
		lawyerRepository.saveAll(Arrays.asList(l1, l2));
		clientRepository.saveAll(Arrays.asList(cl1, cl2,cl3));
		userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5));
		
	}
}
