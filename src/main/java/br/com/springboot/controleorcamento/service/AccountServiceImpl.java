package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.repository.ContaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final ContaRepository contaRepository;
    private final UsuarioService usuarioService;

    public AccountServiceImpl(ContaRepository contaRepository, UsuarioService usuarioService) {
        this.contaRepository = contaRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        log.debug("CategoryService - findAll");
        return contaRepository.findByUsuario(usuarioService.getCurrentUser(), pageable);
    }

    @Override
    public Account save(Account conta, Usuario usuario) {
        conta.setUsuario(usuario);
        return contaRepository.save(conta);
    }

    @Override
    public Account findOne(Long id) {
        log.debug("Service - findOne");

        Optional<Account> contaOptional = contaRepository.findById(id);

        if (!contaOptional.isPresent()) {
            throw new ResourceNotFoundException("Nenhum conta encontrado no id", null);
        }

        return contaOptional.get();
    }

    @Override
    public void update(Account conta) {
        verificaSeContaExiste(conta.getId());
        contaRepository.save(conta);
    }

    private void verificaSeContaExiste(Long id) {
        if (!contaRepository.existsById(id))
            throw new ResourceNotFoundException("Nenhum conta encontrado no id", null);
    }
}
