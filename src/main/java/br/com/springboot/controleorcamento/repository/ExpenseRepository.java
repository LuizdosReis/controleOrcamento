package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Repository
@Transactional
public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {


    Page<Expense> findByCategory(Category category, Pageable pageable);

    Page<Expense> findByDateBetween(LocalDate dataInitial, LocalDate dataEnd, Pageable pageable);

    Page<Expense> findAllByAccountIn(Set<Account> accounts, Pageable pageable);
}
