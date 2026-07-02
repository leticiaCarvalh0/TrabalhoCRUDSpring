package br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity(name = "contratos")
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// Locatário (inquilino): selecionado a partir da tabela de usuários.
	// A obrigatoriedade da seleção é verificada no ContractController.
	@ManyToOne(optional = false)
	private User tenant;

	@NotBlank(message = "A data de início do contrato não pode ser vazia.")
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "A data deve estar no formato AAAA-MM-DD.")
	private String startDate;

	@NotNull(message = "O tempo de vigência do contrato não pode ser nulo.")
	@Positive(message = "A vigência em meses deve ser maior que zero.")
	private Integer durationMonths;

	// Imóvel: selecionado a partir da tabela de imóveis. O valor do aluguel
	// não é digitado no contrato — vem do imóvel escolhido (property.rentValue).
	// A obrigatoriedade da seleção é verificada no ContractController.
	@ManyToOne(optional = false)
	private Property property;

	public Contract() {
	}

	Contract(Integer id) {
		this.id = id;
		setStartDate("");
		setDurationMonths(0);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getTenant() {
		return tenant;
	}

	public void setTenant(User tenant) {
		this.tenant = tenant;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Integer getDurationMonths() {
		return durationMonths;
	}

	public void setDurationMonths(Integer durationMonths) {
		this.durationMonths = durationMonths;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}
}
