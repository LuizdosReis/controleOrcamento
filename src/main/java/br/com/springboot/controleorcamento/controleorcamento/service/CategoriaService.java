package br.com.springboot.controleorcamento.controleorcamento.service;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaService {
    Categoria save(Categoria categoria, Usuario usuario);

    Page<Categoria> findByUsuario(Usuario usuario, Pageable pageable);

    void update(Categoria categoria);
}