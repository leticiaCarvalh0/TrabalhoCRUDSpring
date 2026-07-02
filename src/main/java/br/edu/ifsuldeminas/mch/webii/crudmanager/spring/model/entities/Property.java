package br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity(name="imoveis")
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotBlank(message="O endereço do imóvel não pode ser vazio.")
	private String address;
	
	@NotBlank(message="O tipo do imóvel (Casa/Ap) não pode ser vazio.")
	private String type;
	
	@NotNull(message="O valor do aluguel não pode ser nulo.")
	@Positive(message="O valor do aluguel deve ser maior que zero.")
	private Double rentValue;
	
	@NotBlank(message="A cidade não pode ser vazia.")
	private String city;

	// Indica se o imóvel está ocupado (vinculado a um contrato) ou disponível.
	private Boolean occupied = false;

	public Property() {}

	Property(Integer id){
		this.id = id;
		setAddress("");
		setType("");
		setRentValue(0.0);
		setCity("");
		setOccupied(false);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getRentValue() {
		return rentValue;
	}

	public void setRentValue(Double rentValue) {
		this.rentValue = rentValue;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Boolean getOccupied() {
		return occupied;
	}

	public void setOccupied(Boolean occupied) {
		this.occupied = occupied;
	}
}