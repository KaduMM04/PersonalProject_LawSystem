package com.example.MyProject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseDTO {
	
	private Long id;
	private String typeCase;
	private String description;
	private Double price;
	private LawyerBasicInfoDTO lawyer;
	private ClientBasicInfoDTO client;
	
	public CaseDTO() {}
	
	public CaseDTO(Long id, String typeCase, String description, Double price, LawyerBasicInfoDTO lawyer,
			ClientBasicInfoDTO client) {
		super();
		this.id = id;
		this.typeCase = typeCase;
		this.description = description;
		this.price = price;
		this.lawyer = lawyer;
		this.client = client;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeCase() {
		return typeCase;
	}

	public void setTypeCase(String typeCase) {
		this.typeCase = typeCase;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LawyerBasicInfoDTO getLawyer() {
		return lawyer;
	}

	public void setLawyer(LawyerBasicInfoDTO lawyer) {
		this.lawyer = lawyer;
	}

	public ClientBasicInfoDTO getClient() {
		return client;
	}

	public void setClient(ClientBasicInfoDTO client) {
		this.client = client;
	}

}
