package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.CategoriaDto;
import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaService {
    CategoriaDto save(CategoriaDto categoriaDto, Usuario usuario);

    Page<CategoriaDto> findByUsuario(Usuario usuario, Pageable pageable);

    void update(Categoria categoria,Usuario usuario);

    Categoria findOne(Long id);

    void verificaSeCategoriaPertencemAoUsuario(Categoria categoria, Usuario usuario);

    void delete(Long id);

    Categoria findById(Long id, Usuario usuario);
}
