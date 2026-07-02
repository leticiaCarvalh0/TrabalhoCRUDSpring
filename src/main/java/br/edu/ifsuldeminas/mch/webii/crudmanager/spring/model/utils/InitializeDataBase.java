package br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.utils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities.Address;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities.Contract;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities.Property;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities.User;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.repositories.AdrressRepository;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.repositories.ContractRepository;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.repositories.PropertyRepository;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class InitializeDataBase implements CommandLineRunner {
	
	@Autowired
	private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdrressRepository adrressRepository;

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private PropertyRepository propertyRepository;

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if (userRepository.count() == 0) {
			Address aEmerson = new Address();
			aEmerson.setPlace("Rua emerson");
			aEmerson.setNumber("10");
			aEmerson.setZipCode("130000");
			adrressRepository.save(aEmerson);

			Address aLuiza = new Address();
			aLuiza.setPlace("Rua luiza");
			aLuiza.setNumber("11");
			aLuiza.setZipCode("131000");
			adrressRepository.save(aLuiza);

			Address aAna = new Address();
			aAna.setPlace("Rua ana");
			aAna.setNumber("12");
			aAna.setZipCode("132000");
			adrressRepository.save(aAna);

			adrressRepository.flush();

			User emerson = new User();
			emerson.setName("Emerson Carvalho");
			emerson.setGender("M");
			emerson.setEmail("emerson@mail.com");
			emerson.setPassword(encoder.encode("123456"));
			emerson.setAddress(aEmerson);
			

			User luiza = new User();
			luiza.setName("Luiza Carvalho");
			luiza.setGender("F");
			luiza.setEmail("lu@mail.com");
			luiza.setPassword(encoder.encode("123456"));
			luiza.setAddress(aLuiza);
	

			User ana = new User();
			ana.setName("Ana Carvalho");
			ana.setGender("F");
			ana.setEmail("ana@mail.com");
			ana.setPassword(passwordEncoder.encode("123456")); 
			ana.setAddress(aAna);
			

			userRepository.save(emerson);
			userRepository.save(luiza);
			userRepository.save(ana);
		}

		if (contractRepository.count() == 0) {
			// p1 e p2 terão contrato (ocupados); p3 fica disponível (para demonstração).
			Property p1 = new Property();
			p1.setAddress("Av. Paulista, 1000");
			p1.setType("Apartamento");
			p1.setRentValue(2500.00);
			p1.setCity("São Paulo");
			p1.setOccupied(true);
			propertyRepository.save(p1);

			Property p2 = new Property();
			p2.setAddress("Rua das Flores, 25");
			p2.setType("Casa");
			p2.setRentValue(1800.00);
			p2.setCity("Campinas");
			p2.setOccupied(true);
			propertyRepository.save(p2);

			Property p3 = new Property();
			p3.setAddress("Rua do Comércio, 300");
			p3.setType("Apartamento");
			p3.setRentValue(1500.00);
			p3.setCity("Machado");
			p3.setOccupied(false);
			propertyRepository.save(p3);

			propertyRepository.flush();

			// Locatários vêm da tabela de usuários já cadastrada.
			java.util.List<User> users = userRepository.findAll();
			User tenant1 = users.get(0);
			User tenant2 = users.size() > 1 ? users.get(1) : users.get(0);

			Contract c1 = new Contract();
			c1.setTenant(tenant1);
			c1.setStartDate("2025-01-01");
			c1.setDurationMonths(12);
			c1.setProperty(p1);

			Contract c2 = new Contract();
			c2.setTenant(tenant2);
			c2.setStartDate("2025-03-15");
			c2.setDurationMonths(6);
			c2.setProperty(p2);

			contractRepository.save(c1);
			contractRepository.save(c2);
		}
	}
}
