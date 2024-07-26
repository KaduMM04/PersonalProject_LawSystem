package com.example.MyProject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LawyerBasicInfoDTO {
	
	private Long oab;
	private String name;
	
	public LawyerBasicInfoDTO(Long oab, String name) {
		this.oab = oab;
		this.name = name;
	}
	
	public Long getOab() {
		return oab;
	}
	
	public void setOab(Long oab) {
		this.oab = oab;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
