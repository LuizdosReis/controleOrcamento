package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.CategoriaUpdateDto;
import br.com.springboot.controleorcamento.dto.CategoryCreateDto;
import br.com.springboot.controleorcamento.dto.CategoryDto;
import br.com.springboot.controleorcamento.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDto save(CategoryCreateDto categoriaDto);

    Category save(Category category);

    Page<CategoryDto> findAll(Pageable pageable);

    List<CategoryDto> findAll();

    void update(CategoriaUpdateDto categoriaDto);

    void delete(Long id);

    CategoryDto findOne(Long id);

    Category findBy(Long id);
}
