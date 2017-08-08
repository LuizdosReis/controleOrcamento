package br.com.springboot.controleorcamento.controleorcamento.repository;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;

public interface GastoRepository extends PagingAndSortingRepository<Gasto, Long> {

}
