package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Page<Category> findByUser(User user, Pageable pageable);

    List<Category> findByUser(User user);

    Optional<Category> findByIdAndUser(Long id, User user);
}
