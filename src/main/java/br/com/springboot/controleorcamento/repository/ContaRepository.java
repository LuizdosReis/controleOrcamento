package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends PagingAndSortingRepository<Conta, Long> {
    Page<Conta> findByUsuario(Usuario usuario, Pageable pageable);
}
