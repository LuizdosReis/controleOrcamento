package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    Page<Account> findAll(Pageable pageable);

    Account save(Account conta, Usuario usuario);

    Account findOne(Long id);

    void update(Account conta);
}
