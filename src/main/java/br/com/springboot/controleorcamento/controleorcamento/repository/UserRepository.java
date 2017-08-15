package br.com.springboot.controleorcamento.controleorcamento.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<Usuario, Long>{

	Usuario findByUsername(String username);
}
