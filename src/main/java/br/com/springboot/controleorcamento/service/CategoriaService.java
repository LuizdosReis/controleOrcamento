package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.CategoriaCreateDto;
import br.com.springboot.controleorcamento.dto.CategoriaDto;
import br.com.springboot.controleorcamento.dto.CategoriaUpdateDto;
import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriaService {
    CategoriaDto save(CategoriaCreateDto categoriaDto);

    Categoria save(Categoria categoria);

    Page<CategoriaDto> findAll(Pageable pageable);

    List<CategoriaDto> findAll();

    void update(CategoriaUpdateDto categoriaDto);

    void verificaSeCategoriaPertencemAoUsuario(Categoria categoria, Usuario usuario);

    void delete(Long id);

    CategoriaDto findOne(Long id);

    Categoria findBy(Long id);
}
