package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Expense;
import br.com.springboot.controleorcamento.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;


public interface ExpenseService {
    Expense save(Expense expense);

    void delete(long id);

    Page<Expense> findAll(User usuario, Pageable pageable);

    Expense getById(Long id);

    Page<Expense> findByCategory(long idCategoria, Pageable pageable);

    Page<Expense> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable);

    void update(Expense expense);
}
