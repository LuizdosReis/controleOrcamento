package br.com.springboot.controleorcamento.controleorcamento.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.springboot.controleorcamento.controleorcamento.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>{

	User findByUsername(String username);
}
