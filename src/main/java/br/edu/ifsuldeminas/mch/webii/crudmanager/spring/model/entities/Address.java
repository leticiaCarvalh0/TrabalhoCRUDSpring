package br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity(name = "enderecos")
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotBlank(message = "O logradouro não pode ser vazio.")
	private String place;
	
	@NotBlank(message = "O número não pode ser vazio.")
	@Length(max = 15, message = "Núnero precisa ser menor que 15 dígitos.")
	private String number;
	
	@NotBlank(message = "CEP não pode ser vazio.")
	@Length(max = 8, message = "CEP deve ter no máximo 8 dígitos.")
	private String zipCode;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
