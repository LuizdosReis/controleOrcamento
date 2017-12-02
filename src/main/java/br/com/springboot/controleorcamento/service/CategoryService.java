package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.CategoriaCreateDto;
import br.com.springboot.controleorcamento.dto.CategoryDto;
import br.com.springboot.controleorcamento.dto.CategoriaUpdateDto;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDto save(CategoriaCreateDto categoriaDto);

    Category save(Category category);

    Page<CategoryDto> findAll(Pageable pageable);

    List<CategoryDto> findAll();

    void update(CategoriaUpdateDto categoriaDto);

    void verificaSeCategoriaPertencemAoUsuario(Category category, Usuario usuario);

    void delete(Long id);

    CategoryDto findOne(Long id);

    Category findBy(Long id);
}
