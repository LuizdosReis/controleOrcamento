package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends PagingAndSortingRepository<Account, Long>, JpaSpecificationExecutor<Account> {

    Page<Account> findByUsuario(Usuario usuario, Pageable pageable);
}
