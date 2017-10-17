package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Categoria save(Categoria categoria, Usuario usuario) {
        categoria.setUsuario(usuario);
        categoria = categoriaRepository.save(categoria);
        return categoria;
    }

    @Override
    public Page<Categoria> findByUsuario(Usuario usuario, Pageable pageable) {
        return categoriaRepository.findByUsuario(usuario,pageable);
    }

    @Override
    public void update(Categoria categoria,Usuario usuario) {
        verificaSeGastoExiste(categoria.getId());

        verificaSeCategoriaPertencemAoUsuario(categoria,usuario);

        categoria.setUsuario(usuario);

        categoriaRepository.save(categoria);
    }

    @Override
    public Categoria findOne(Long id) {
        verificaSeGastoExiste(id);
        return categoriaRepository.findById(id).get();
    }

    @Override
    public void verificaSeCategoriaPertencemAoUsuario(Categoria categoria, Usuario usuario) {
       if(!categoriaRepository.findByUsuario(usuario).contains(categoria))
           throw new IllegalArgumentException("Categoria não pertence ao usuário logado");
    }

    @Override
    public void delete(Long id) {
        verificaSeGastoExiste(id);
        categoriaRepository.deleteById(id);
    }

    @Override
    public Categoria findById(Long id, Usuario usuario) {
        verificaSeGastoExiste(id);

        Categoria categoria = categoriaRepository.findById(id).get();

        verificaSeCategoriaPertencemAoUsuario(categoria,usuario);

        return categoria;
    }

    private void verificaSeGastoExiste(Long id) {
        if (!categoriaRepository.existsById(id))
            throw new ResourceNotFoundException("Nenhuma categoria encontrado no id", null);
    }
}
