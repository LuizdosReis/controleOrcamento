package br.com.springboot.controleorcamento.controleorcamento.repository;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Long> {
    public Categoria findById(Long id);

}
