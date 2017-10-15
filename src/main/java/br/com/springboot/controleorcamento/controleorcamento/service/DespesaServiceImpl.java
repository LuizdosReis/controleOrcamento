package br.com.springboot.controleorcamento.controleorcamento.service;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.repository.DespesaRepository;
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
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;
    private final ContaService contaService;

    public DespesaServiceImpl(DespesaRepository despesaRepository,
                              CategoriaService categoriaService,
                              UsuarioService usuarioService,
                              ContaService contaService) {
        this.despesaRepository = despesaRepository;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
        this.contaService = contaService;
    }

    @Override
    public Despesa save(Despesa despesa) {

        log.debug("Metodo save");

        Conta conta = contaService.findOne(despesa.getConta().getId());

        return adicionaDespesa(despesa,conta);
    }

    @Override
    public void update(Despesa despesa) {
        verificaSeDespesaExiste(despesa.getId());

        Conta conta = contaService.findOne(despesa.getConta().getId());

        conta.getDespesas().stream()
                .filter(d -> d.getId().equals(despesa.getId()))
                .findFirst()
                .ifPresent(d -> conta.removeDespesa(d));

        adicionaDespesa(despesa,conta);
    }

    private Despesa adicionaDespesa(Despesa despesa,Conta conta) {
        despesa.setCategoria(categoriaService.findOne(despesa.getCategoria().getId()));

        conta.adicionaDespesa(despesa);

        despesa.setConta(conta);

        verificaCategoria(despesa, conta.getUsuario());

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
        Categoria categoria = categoriaService.findOne(idCategoria);
        return despesaRepository.findByCategoria(categoria,pageable);
    }

    @Override
    public Page<Despesa> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable) {
        return despesaRepository.findByDataBetween(dataInicial,dataFinal,pageable);
    }

    private void verificaSeDespesaExiste(Long id) {
        if (despesaRepository.existsById(id))
            throw new ResourceNotFoundException("Nenhum gasto encontrado no id", null);
    }

    private void verificaCategoria(Despesa despesa, Usuario usuario) {

        if(!categoriaService.verificaSeCategoriaPertencemAoUsuario(despesa.getCategoria(),usuario)){
            throw new ResourceNotFoundException("Categoria não pertence ao usuário",null);
        }
    }
}
