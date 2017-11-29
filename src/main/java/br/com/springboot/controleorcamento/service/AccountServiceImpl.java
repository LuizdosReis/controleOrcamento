package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UsuarioService usuarioService;

    public AccountServiceImpl(AccountRepository accountRepository, UsuarioService usuarioService) {
        this.accountRepository = accountRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        log.debug("CategoryService - findAll");
        return accountRepository.findByUsuario(usuarioService.getCurrentUser(), pageable);
    }

    @Override
    public Account save(Account conta, Usuario usuario) {
        conta.setUsuario(usuario);
        return accountRepository.save(conta);
    }

    @Override
    public Account findOne(Long id) {
        log.debug("Service - findOne");

        Optional<Account> accountOptional = accountRepository.findById(id);

        if (!accountOptional.isPresent()) {
            log.error("Service - no account found with id");
            throw new ResourceNotFoundException("Nenhuma conta encontrado no id", null);
        }

        return accountOptional.get();
    }

    @Override
    public void update(Account conta) {
        verificaSeContaExiste(conta.getId());
        accountRepository.save(conta);
    }

    private void verificaSeContaExiste(Long id) {
        if (!accountRepository.existsById(id))
            throw new ResourceNotFoundException("Nenhum conta encontrado no id", null);
    }
}
