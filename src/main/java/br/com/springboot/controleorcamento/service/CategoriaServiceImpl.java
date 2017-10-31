package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.CategoriaCreateDto;
import br.com.springboot.controleorcamento.dto.CategoriaDto;
import br.com.springboot.controleorcamento.dto.CategoriaUpdateDto;
import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    private static final String NENHUMA_CATEGORIA_ENCONTRADO_NO_ID = "Nenhuma categoria encontrado no id";
    private final CategoriaRepository categoriaRepository;

    private final ModelMapper modelMapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoriaDto save(CategoriaCreateDto categoriaDto, Usuario usuario) {
        log.debug("CategoriaService - save");

        Categoria categoria = modelMapper.map(categoriaDto,Categoria.class);

        categoria.setUsuario(usuario);
        categoria = categoriaRepository.save(categoria);
        return modelMapper.map(categoria,CategoriaDto.class);
    }

    @Override
    public Categoria save(Categoria categoria, Usuario usuario) {
        log.debug("CategoriaService - save");

        categoria.setUsuario(usuario);

        return categoriaRepository.save(categoria);
    }

    @Override
    public Page<CategoriaDto> findByUsuario(Usuario usuario, Pageable pageable) {
        log.debug("CategoriaService - findByUsuario");
        Page<Categoria> page = categoriaRepository.findByUsuario(usuario, pageable);

        List<CategoriaDto> categoriasDtos = page.getContent()
                .stream()
                .map(categoria -> modelMapper.map(categoria,CategoriaDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(categoriasDtos, pageable, page.getTotalElements());
    }

    @Override
    public List<CategoriaDto> findByUsuario(Usuario usuario) {
        List<Categoria> categorias = categoriaRepository.findByUsuario(usuario);

        return categorias.stream()
                .map(categoria -> modelMapper.map(categoria,CategoriaDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(CategoriaUpdateDto categoriaDto, Usuario usuario) {
        Categoria categoria = modelMapper.map(categoriaDto, Categoria.class);

        verificaSeGastoExiste(categoria.getId());

        verificaSeCategoriaPertencemAoUsuario(categoria,usuario);

        categoria.setUsuario(usuario);

        categoriaRepository.save(categoria);
    }


    @Override
    public void verificaSeCategoriaPertencemAoUsuario(Categoria categoria, Usuario usuario) {
       if(!categoriaRepository.findByUsuario(usuario).contains(categoria))
           throw new IllegalArgumentException("Categoria não pertence ao usuário logado");
    }

    @Override
    public void delete(Long id, Usuario usuario) {
        verificaSeGastoExiste(id);
        categoriaRepository.deleteById(id);
    }

    @Override
    public CategoriaDto findById(Long id, Usuario usuario) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NENHUMA_CATEGORIA_ENCONTRADO_NO_ID, null));

        verificaSeCategoriaPertencemAoUsuario(categoria,usuario);

        return modelMapper.map(categoria,CategoriaDto.class);
    }

    @Override
    public Categoria findOne(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NENHUMA_CATEGORIA_ENCONTRADO_NO_ID, null));
    }

    private void verificaSeGastoExiste(Long id) {
        if (!categoriaRepository.existsById(id))
            throw new ResourceNotFoundException(NENHUMA_CATEGORIA_ENCONTRADO_NO_ID, null);
    }
}
