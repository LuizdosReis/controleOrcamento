package br.com.springboot.controleorcamento.controleorcamento.service;

import br.com.springboot.controleorcamento.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.repository.ContaRepository;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        verificaSeContaExiste(id);
        return contaRepository.findOne(id);
    }

    private Conta verificaSeContaExiste(Long id) {
        Conta conta = contaRepository.findOne(id);

        if (conta == null)
            throw new ResourceNotFoundException("Nenhum gasto encontrado no id", null);
        return conta;
    }
}
