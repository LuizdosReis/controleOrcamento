package br.com.springboot.controleorcamento.controleorcamento.service;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.repository.CategoriaRepository;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Categoria save(Categoria categoria, Usuario usuario) {
        categoria.setUsuario(usuario);
        return categoriaRepository.save(categoria);
    }

    @Override
    public Page<Categoria> findByUsuario(Usuario usuario, Pageable pageable) {
        return categoriaRepository.findByUsuario(usuario,pageable);
    }

    @Override
    public void update(Categoria categoria) {
        verificaSeGastoExiste(categoria.getId());
        categoriaRepository.save(categoria);
    }

    @Override
    public Categoria findOne(Long id) {
        verificaSeGastoExiste(id);
        return categoriaRepository.findById(id).get();
    }

    @Override
    public boolean verificaSeCategoriasPertencemAoUsuario(Categoria categoria, Usuario usuario) {
        return categoriaRepository.findByUsuario(usuario).contains(categoria);
    }

    private void verificaSeGastoExiste(Long id) {
        if (!categoriaRepository.existsById(id))
            throw new ResourceNotFoundException("Nenhuma categoria encontrado no id", null);
    }
}
