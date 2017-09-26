package br.com.springboot.controleorcamento.controleorcamento.service;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.repository.DespesaRepository;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DespesaServiceImpl implements DespesaService {

    private final DespesaRepository despesaRepository;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    public DespesaServiceImpl(DespesaRepository despesaRepository, CategoriaService categoriaService,UsuarioService usuarioService) {
        this.despesaRepository = despesaRepository;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
    }

    @Override
    public Despesa save(Despesa despesa) {
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
        despesaRepository.save(despesa);
    }

    private Despesa verificaSeDespesaExiste(Long id) {
        Despesa gasto = despesaRepository.findOne(id);

        if (gasto == null)
            throw new ResourceNotFoundException("Nenhum gasto encontrado no id", null);
        return gasto;
    }
}
