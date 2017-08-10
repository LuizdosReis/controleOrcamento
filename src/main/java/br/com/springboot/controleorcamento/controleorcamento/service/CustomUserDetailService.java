package br.com.springboot.controleorcamento.controleorcamento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	private final UserRepository repository;
	

	@Autowired
	public CustomUserDetailService(UserRepository repository) {
		this.repository = repository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = Optional.ofNullable(repository.findByUsername(username))
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN");
		List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(), 
				user.isAdmin() ? authorityListAdmin : authorityListUser);
	}

}
