package br.edu.ifsuldeminas.mch.webii.crudmanager.spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities.Contract;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities.Property;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.repositories.ContractRepository;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.repositories.PropertyRepository;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.repositories.UserRepository;
import jakarta.validation.Valid;

@Controller
public class ContractController {

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private UserRepository userRepository;


	@ModelAttribute("usuarios")
	public List<br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities.User> allUsers() {
		return userRepository.findAll();
	}

	@ModelAttribute("imoveis")
	public List<Property> allProperties() {
		return propertyRepository.findAll();
	}

	@GetMapping("/contracts")
	public String listContracts(Model model) {

		List<Contract> contracts = contractRepository.findAll();
		model.addAttribute("contratos", contracts);

		return "contracts";
	}

	@GetMapping("/contracts/form")
	public String contractForm(Model model) {
		Contract contract = new Contract();
		contract.setTenant(new br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities.User());
		contract.setProperty(new Property());
		model.addAttribute("contract", contract);
		return "contract_form";
	}

	@PostMapping("/contracts/save")
	public String contractSave(@ModelAttribute("contract") @Valid Contract contract,
	                           BindingResult errors) {

		
		if (contract.getTenant() == null || contract.getTenant().getId() == null)
			errors.rejectValue("tenant", "required", "Selecione o locatário (usuário).");
		if (contract.getProperty() == null || contract.getProperty().getId() == null)
			errors.rejectValue("property", "required", "Selecione o imóvel.");

		if (errors.hasErrors())
			return "contract_form";

		
		contract.setTenant(userRepository.findById(contract.getTenant().getId()).get());
		Property property = propertyRepository.findById(contract.getProperty().getId()).get();
		property.setOccupied(true);
		propertyRepository.save(property);
		contract.setProperty(property);

		contractRepository.save(contract);

		return "redirect:/contracts";
	}

	@GetMapping("/contracts/{id}")
	public String contractUpdate(@PathVariable("id") Integer id, Model model) {

		Optional<Contract> contractOpt = contractRepository.findById(id);

		Contract contract = null;
		if (contractOpt.isPresent())
			contract = contractOpt.get();

		model.addAttribute("contract", contract);

		return "contract_form";
	}

	@GetMapping("/contracts/delete/{id}")
	public String contractDelete(@PathVariable("id") Integer id) {

		Optional<Contract> contractOpt = contractRepository.findById(id);

		if (contractOpt.isPresent()) {
			Property property = contractOpt.get().getProperty();

			contractRepository.delete(contractOpt.get());

			// Ao excluir o contrato, o imóvel volta a ficar disponível.
			property.setOccupied(false);
			propertyRepository.save(property);
		}

		return "redirect:/contracts";
	}
}
