package com.example.MyProject.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "scretaries")
public class Secretary extends User{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer registrationNum;

	public Secretary(String name, String email, String password, UserRole role, Integer registrationNum) {
		super(name, email, password, role);
		this.registrationNum = registrationNum;
	}

	public Integer getRegistrationNum() {
		return registrationNum;
	}

	public void setRegistrationNum(Integer registrationNum) {
		this.registrationNum = registrationNum;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(registrationNum);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Secretary other = (Secretary) obj;
		return Objects.equals(registrationNum, other.registrationNum);
	}
}
