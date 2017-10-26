package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Conta;
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
public class ContaServiceImpl implements ContaService{

    private final ContaRepository contaRepository;

    public ContaServiceImpl(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public Page<Conta> findByUsuario(Usuario usuario, Pageable pageable) {
        return contaRepository.findByUsuario(usuario,pageable);
    }

    @Override
    public Conta save(Conta conta, Usuario usuario) {
        conta.setUsuario(usuario);
        return contaRepository.save(conta);
    }

    @Override
    public Conta findOne(Long id) {
        log.debug("Service - findOne");

        Optional<Conta> contaOptional = contaRepository.findById(id);

        if(!contaOptional.isPresent()){
            throw new ResourceNotFoundException("Nenhum conta encontrado no id", null);
        }

        return contaOptional.get();
    }

    @Override
    public void update(Conta conta) {
        verificaSeContaExiste(conta.getId());
        contaRepository.save(conta);
    }

    private void verificaSeContaExiste(Long id) {
        if (!contaRepository.existsById(id))
            throw new ResourceNotFoundException("Nenhum conta encontrado no id", null);
    }
}
