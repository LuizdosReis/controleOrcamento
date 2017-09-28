package br.com.springboot.controleorcamento.controleorcamento.service;

import br.com.springboot.controleorcamento.controleorcamento.model.*;
import br.com.springboot.controleorcamento.controleorcamento.repository.DespesaRepository;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
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

        Conta conta = contaService.findOne(despesa.getConta().getId());

        return adicionaDespesa(despesa, conta);
    }

    private Despesa adicionaDespesa(Despesa despesa, Conta conta) {
        conta.adicionaDespesa(despesa);

        verificaCategorias(despesa, conta.getUsuario());

        contaService.update(conta);

        despesa.setConta(conta);

        return despesaRepository.save(despesa);
    }


    @Override
    public void delete(long id) {
        verificaSeDespesaExiste(id);
        despesaRepository.delete(id);
    }

    @Override
    public Page<Despesa> findAll(Usuario usuario, Pageable pageable) {
        usuario = usuarioService.loadUserByUsername(usuario.getUsername());

        return despesaRepository.findAllByContaIn(usuario.getContas(),pageable);
    }

    @Override
    public Despesa getById(Long id) {
        verificaSeDespesaExiste(id);
        return despesaRepository.findOne(id);
    }

    @Override
    public Page<Despesa> findByCategoria(long idCategoria, Pageable pageable) {
        Categoria categoria = categoriaService.findOne(idCategoria);
        return despesaRepository.findByDespesasCategorizadas(categoria,pageable);
    }

    @Override
    public Page<Despesa> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable) {
        return despesaRepository.findByDataBetween(dataInicial,dataFinal,pageable);
    }

    @Override
    public void update(Despesa despesa) {
        verificaSeDespesaExiste(despesa.getId());

        Conta conta = contaService.findOne(despesa.getConta().getId());

        conta.getDespesas().stream()
                .filter(d -> d.getId() == despesa.getId())
                .findFirst()
                .ifPresent(d -> conta.removeDespesa(d));

        adicionaDespesa(despesa,conta);
    }

    private Despesa verificaSeDespesaExiste(Long id) {
        Despesa gasto = despesaRepository.findOne(id);

        if (gasto == null)
            throw new ResourceNotFoundException("Nenhum gasto encontrado no id", null);
        return gasto;
    }

    private void verificaCategorias(Despesa despesa, Usuario usuario) {
        List<Categoria> categorias = despesa.getDespesasCategorizadas()
                .stream()
                .map(DespesaCategorizada::getCategoria)
                .collect(Collectors.toList());

        if(!categoriaService.verificaSeCategoriasPertencemAoUsuario(categorias,usuario)){
            throw new ResourceNotFoundException("Categoria não pertence ao usuário",null);
        }
    }
}
