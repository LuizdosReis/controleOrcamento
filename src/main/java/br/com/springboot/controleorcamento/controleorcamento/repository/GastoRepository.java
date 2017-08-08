package br.com.springboot.controleorcamento.controleorcamento.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;

public interface GastoRepository extends PagingAndSortingRepository<Gasto, Long> {
	public Gasto findByTipo(String tipo);

}
