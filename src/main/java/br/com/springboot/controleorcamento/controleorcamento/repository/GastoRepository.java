package br.com.springboot.controleorcamento.controleorcamento.repository;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface GastoRepository extends PagingAndSortingRepository<Gasto, Long> {
}
