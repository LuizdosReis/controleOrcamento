package br.com.springboot.controleorcamento.controleorcamento.service;

import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;


public interface DespesaService {
    Despesa save(Despesa despesa);

    void delete(long id);

    Page<Despesa> findAll(Usuario usuario, Pageable pageable);

    Despesa getById(Long id);

    Page<Despesa> findByCategoria(long idCategoria,Pageable pageable);

    Page<Despesa> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable);

    void update(Despesa despesa);
}
