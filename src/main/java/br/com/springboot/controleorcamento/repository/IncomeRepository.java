package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.Income;
import br.com.springboot.controleorcamento.model.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncomeRepository extends PagingAndSortingRepository<Income, Long> {

    Optional<Income> findByIdAndAccount_Usuario(Long id, Usuario usuario);
}
