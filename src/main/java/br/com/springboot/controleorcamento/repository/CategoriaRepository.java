package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends PagingAndSortingRepository<Category, Long> {

    Page<Category> findByUsuario(Usuario usuario, Pageable pageable);

    List<Category> findByUsuario(Usuario usuario);

    Optional<Category> findByIdAndUsuario(Long id, Usuario usuario);
}
