package br.com.springboot.controleorcamento.controleorcamento.repository;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastoRepository extends PagingAndSortingRepository<Gasto, Long> {

    @Query("select g from Gasto g join g.gastosCategorizados gc join gc.categoria c where c = :categoria")
    List<Gasto> findByCategoria(@Param("categoria") Categoria categoria, Pageable pageable);
}
