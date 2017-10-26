package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.converter.CategoriaConverter;
import br.com.springboot.controleorcamento.dto.CategoriaDto;
import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaDto save(CategoriaDto categoriaDto, Usuario usuario) {
        log.debug("CategoriaService - save");

        Categoria categoria = CategoriaConverter.convertToEntity(categoriaDto);

        categoria.setUsuario(usuario);
        categoria = categoriaRepository.save(categoria);
        return CategoriaConverter.convertToDto(categoria);
    }

    @Override
    public Page<CategoriaDto> findByUsuario(Usuario usuario, Pageable pageable) {

        Page<Categoria> page = categoriaRepository.findByUsuario(usuario, pageable);

        List<CategoriaDto> categoriasDtos = page.getContent()
                .stream()
                .map(CategoriaConverter::convertToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(categoriasDtos, pageable, page.getTotalElements());
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
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhuma categoria encontrado no id", null));
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
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhuma categoria encontrado no id", null));

        verificaSeCategoriaPertencemAoUsuario(categoria,usuario);

        return categoria;
    }

    private void verificaSeGastoExiste(Long id) {
        if (!categoriaRepository.existsById(id))
            throw new ResourceNotFoundException("Nenhuma categoria encontrado no id", null);
    }
}
