package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.Income;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends PagingAndSortingRepository<Income, Long> {
}
