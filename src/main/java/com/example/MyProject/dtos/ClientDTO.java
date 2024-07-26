package com.example.MyProject.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
	
	private Long cpf;
	private String name;
	private String email;
	private String cep;
	private List<Long> caseIds;
	
	public ClientDTO() {}

	public ClientDTO(Long cpf, String name, String email, String cep, List<Long> caseIds) {
		super();
		this.cpf = cpf;
		this.name = name;
		this.email = email;
		this.cep = cep;
		this.caseIds = caseIds;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public List<Long> getCaseIds() {
		return caseIds;
	}

	public void setCaseIds(List<Long> caseIds) {
		this.caseIds = caseIds;
	}
}
