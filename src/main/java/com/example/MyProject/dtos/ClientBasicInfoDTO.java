package com.example.MyProject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientBasicInfoDTO {
	
	private Long cpf;
	private String name;
	
	public ClientBasicInfoDTO(Long cpf, String name) {
		super();
		this.cpf = cpf;
		this.name = name;
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
}
