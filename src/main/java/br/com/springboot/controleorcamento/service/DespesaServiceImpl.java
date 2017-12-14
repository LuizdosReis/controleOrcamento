package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.repository.DespesaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class DespesaServiceImpl implements DespesaService {

    private final DespesaRepository despesaRepository;
    private final CategoryService categoryService;
    private final UsuarioService usuarioService;
    private final AccountService accountService;

    public DespesaServiceImpl(DespesaRepository despesaRepository,
                              CategoryService categoryService,
                              UsuarioService usuarioService,
                              AccountService accountService) {
        this.despesaRepository = despesaRepository;
        this.categoryService = categoryService;
        this.usuarioService = usuarioService;
        this.accountService = accountService;
    }

    @Override
    public Despesa save(Despesa despesa) {

        log.debug("Metodo save");

        Account conta = accountService.findOne(despesa.getConta().getId());

        return adicionaDespesa(despesa,conta);
    }

    @Override
    public void update(Despesa despesa) {
        verificaSeDespesaExiste(despesa.getId());

        Account conta = accountService.findOne(despesa.getConta().getId());

        conta.getDespesas().stream()
                .filter(d -> d.getId().equals(despesa.getId()))
                .findFirst()
                .ifPresent(d -> conta.removeDespesa(d));

        adicionaDespesa(despesa,conta);
    }

    private Despesa adicionaDespesa(Despesa despesa,Account conta) {
        despesa.setCategory(categoryService.findBy(despesa.getCategory().getId()));

        conta.adicionaDespesa(despesa);

        despesa.setConta(conta);
        return despesaRepository.save(despesa);
    }


    @Override
    public void delete(long id) {
        verificaSeDespesaExiste(id);
        despesaRepository.deleteById(id);
    }

    @Override
    public Page<Despesa> findAll(Usuario usuario, Pageable pageable) {
        usuario = usuarioService.loadUserByUsername(usuario.getUsername());

        return despesaRepository.findAllByContaIn(usuario.getContas(),pageable);
    }

    @Override
    public Despesa getById(Long id) {
        verificaSeDespesaExiste(id);
        return despesaRepository.findById(id).get();
    }

    @Override
    public Page<Despesa> findByCategoria(long idCategoria, Pageable pageable) {
        Category category = categoryService.findBy(idCategoria);
        return despesaRepository.findByCategory(category,pageable);
    }

    @Override
    public Page<Despesa> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable) {
        return despesaRepository.findByDataBetween(dataInicial,dataFinal,pageable);
    }

    private void verificaSeDespesaExiste(Long id) {
        if (despesaRepository.existsById(id))
            throw new ResourceNotFoundException("Nenhum gasto encontrado no id", null);
    }
}
