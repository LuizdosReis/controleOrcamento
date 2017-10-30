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
    CategoriaDto save(CategoriaCreateDto categoriaDto, Usuario usuario);

    Page<CategoriaDto> findByUsuario(Usuario usuario, Pageable pageable);

    List<CategoriaDto> findByUsuario(Usuario usuario);

    void update(CategoriaUpdateDto categoriaDto, Usuario usuario);

    void verificaSeCategoriaPertencemAoUsuario(Categoria categoria, Usuario usuario);

    void delete(Long id);

    CategoriaDto findById(Long id, Usuario usuario);

    Categoria findOne(Long id);
}
