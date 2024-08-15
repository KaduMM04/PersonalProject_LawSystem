package com.example.MyProject.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "clients")
public class Client extends User{
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long cpf;
	
	@Column(name = "cep")
	private String cep;
	
	@OneToMany(mappedBy = "client")
	private List<Case> cases = new ArrayList<>();
	
	public Client() {
	}

	public Client(String name, String email, String password, UserRole role, Long cpf, String cep) {
		super(name, email, password, role);
		this.cpf = cpf;
		this.cep = cep;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public List<Case> getCases() {
		return cases;
	}

	public void setCases(List<Case> cases) {
		this.cases = cases;
	}
	
	public void addCase(Case c) throws IllegalArgumentException {
		
		if (c == null) {
			throw new IllegalArgumentException("Case cannot be null");
		}
		if (cases.contains(c)) {
			throw new IllegalArgumentException("Case already exists.");
		}
		
		cases.add(c);
		c.setClient(this);
	}
	
	public void removeCase(Long searchId) throws IllegalArgumentException{
		
		Case caseToRemove = null;
		
		for (Case temp : cases) {
			if(temp.getId().equals(searchId)) {
				caseToRemove = temp;
				break;
			}
			
		}
		
		if (caseToRemove != null) {
			cases.remove(caseToRemove);
			caseToRemove.setClient(null);
		} else {
			
			throw new IllegalArgumentException("Case with ID " + searchId + "not found.");
			
		}
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(cpf, other.cpf);
	}
}
