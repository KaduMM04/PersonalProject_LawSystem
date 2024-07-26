package com.example.MyProject.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LawyerDTO {

	private Long oab;
    private String name;
    private String email;
    private String praticeArea;
    private List<Long> caseIds;
    
    public LawyerDTO(String name, String email, Long oab, String praticeArea, List<Long> caseIds) {
        this.name = name;
        this.email = email;
        this.oab = oab;
        this.praticeArea = praticeArea;
        this.caseIds = caseIds;
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
	
	public Long getOab() {
		return oab;
	}
	
	public void setOab( Long oab) {
		this.oab = oab;
	}
	
	public String getPraticeArea() {
		return praticeArea;
	}
	
	public void setPraticeArea(String praticeArea) {
		this.praticeArea = praticeArea;
	}
	
	public List<Long> getCaseIds() {
		return caseIds;
	}
	
	public void setCaseIds(List<Long> caseIds) {
		this.caseIds = caseIds;
	}
}
