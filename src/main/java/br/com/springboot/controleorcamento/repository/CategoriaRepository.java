package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Long> {
    Page<Categoria> findByUsuario(Usuario usuario, Pageable pageable);

    List<Categoria> findByUsuario(Usuario usuario);
}
