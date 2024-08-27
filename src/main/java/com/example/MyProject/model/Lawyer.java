package com.example.MyProject.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "lawyers")
public class Lawyer extends User{
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long oab;
	
	@Column(name = "pratice_area")
	private String praticeArea;
	
	@OneToMany(mappedBy = "lawyer")
	@JsonManagedReference
	private List<Case> cases = new ArrayList<>();
	
	public Lawyer() {
	}
	
	public Lawyer(String email, String  password) {
		super(email, password);
	}

	public Lawyer(String name, String email, String password, UserRole role, Long  oab, String praticeArea) {
		super(name, email, password, role);
		this.oab = oab;
		this.praticeArea = praticeArea;
	}

	public Long getOab() {
		return oab;
	}

	public void setOab(Long oab) {
		this.oab = oab;
	}

	public String getPraticeArea() {
		return praticeArea;
	}

	public void setPraticeArea(String praticeArea) {
		this.praticeArea = praticeArea;
	}

	public List<Case> getCases() {
		return cases;
	}

	public void setCases(List<Case> cases) {
		this.cases = cases;
	}
	
	public void addCase(Case c) {
        if (c == null) {
            throw new IllegalArgumentException("Case cannot be null");
        }
        if (cases.contains(c)) {
            throw new IllegalArgumentException("Case already exists.");
        }
        cases.add(c);
        c.setLawyer(this);
    }

	public void removeCase(Long caseId) {
        Case caseToRemove = null;
        for (Case c : cases) {
            if (c.getId().equals(caseId)) {
                caseToRemove = c;
                break;
            }
        }
        if (caseToRemove != null) {
            cases.remove(caseToRemove);
            caseToRemove.setLawyer(null);
        } else {
            throw new IllegalArgumentException("Case with Id " + caseId + " not found.");
        }
    }

	@Override
	public int hashCode() {
		return Objects.hash(oab);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lawyer other = (Lawyer) obj;
		return Objects.equals(oab, other.oab);
	}
}
