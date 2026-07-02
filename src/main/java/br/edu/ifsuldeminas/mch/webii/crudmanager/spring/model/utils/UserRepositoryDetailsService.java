package br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities.User;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.repositories.UserRepository;

@Service
public class UserRepositoryDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		java.util.List<User> users = userRepository.findAll();
		User user = users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst()
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

		
		UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getEmail());
		builder.password(user.getPassword()); 
		builder.roles("USER");

		return builder.build();
	}
}