package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.model.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Repository
@Transactional
public interface DespesaRepository extends PagingAndSortingRepository<Despesa, Long> {


    Page<Despesa> findByCategoria(Categoria categoria, Pageable pageable);

    Page<Despesa> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable);

    Page<Despesa> findAllByContaIn(Set<Conta> contas, Pageable pageable);
}
