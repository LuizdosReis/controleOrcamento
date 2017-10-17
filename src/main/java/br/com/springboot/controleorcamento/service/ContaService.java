package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContaService {
    Page<Conta> findByUsuario(Usuario usuario, Pageable pageable);

    Conta save(Conta conta, Usuario usuario);

    Conta findOne(Long id);

    void update(Conta conta);
}