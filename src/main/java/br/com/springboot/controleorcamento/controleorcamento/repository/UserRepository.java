package br.com.springboot.controleorcamento.controleorcamento.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;

public interface UserRepository extends PagingAndSortingRepository<Usuario, Long>{

	Usuario findByUsername(String username);
}
