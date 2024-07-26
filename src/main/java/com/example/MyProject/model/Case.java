package com.example.MyProject.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cases")

public class Case {
	
	@Id
	private Long id;
	
	@Column(name = "typeCase")
	private String typeCase;
	
	@ManyToOne
	@JoinColumn(name = "cpf_client")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "oab_lawyer")
	@JsonBackReference
	private Lawyer lawyer;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Double price;
	
	public Case() {}

	public Case(Long id, String typeCase, Client client, Lawyer lawyer, String description, Double price) {
	        this.id = id;
	        this.typeCase = typeCase;
	        this.client = client;
	        this.lawyer = lawyer;
	        this.description = description;
	        this.price = price;
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

		public Client getClient() {
			return client;
		}

		public void setClient(Client client) {
			this.client = client;
		}

		public Lawyer getLawyer() {
			return lawyer;
		}

		public void setLawyer(Lawyer lawyer) {
			this.lawyer = lawyer;
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

		@Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj)
	            return true;
	        if (obj == null || getClass() != obj.getClass())
	            return false;
	        Case other = (Case) obj;
	        return Objects.equals(id, other.id);
	    }
}
