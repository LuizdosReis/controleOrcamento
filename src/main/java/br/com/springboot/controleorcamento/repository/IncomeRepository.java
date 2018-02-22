package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.Income;
import br.com.springboot.controleorcamento.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncomeRepository extends PagingAndSortingRepository<Income, Long> {

    Optional<Income> findByIdAndAccount_User(Long id, User user);
}
