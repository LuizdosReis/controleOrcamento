package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.CategoriaUpdateDto;
import br.com.springboot.controleorcamento.dto.CategoryCreateDto;
import br.com.springboot.controleorcamento.dto.CategoryDto;
import br.com.springboot.controleorcamento.model.Category;
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
public class CategoryServiceImpl implements CategoryService {

    private static final String NO_CATEGORY_FOUND_IN_ID = "No categories found in id";
    private final CategoriaRepository categoriaRepository;
    private final UsuarioService usuarioService;

    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoriaRepository categoriaRepository, UsuarioService usuarioService, ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto save(CategoryCreateDto categoriaDto) {
        log.debug("CategoryService - save");

        Category category = modelMapper.map(categoriaDto,Category.class);

        category.setUsuario(usuarioService.getCurrentUser());
        category = categoriaRepository.save(category);
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public Category save(Category category) {
        log.debug("CategoryService - save");

        category.setUsuario(usuarioService.getCurrentUser());

        return categoriaRepository.save(category);
    }

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        log.debug("CategoryService - findAll");
        Page<Category> page = categoriaRepository.findByUsuario(usuarioService.getCurrentUser(), pageable);

        List<CategoryDto> categoriasDtos = page.getContent()
                .stream()
                .map(categoria -> modelMapper.map(categoria,CategoryDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(categoriasDtos, pageable, page.getTotalElements());
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categories = categoriaRepository.findByUsuario(usuarioService.getCurrentUser());

        return categories.stream()
                .map(categoria -> modelMapper.map(categoria,CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(CategoriaUpdateDto categoriaDto) {
        Category category = modelMapper.map(categoriaDto, Category.class);

        verificaSeGastoExiste(category.getId());

        Usuario usuario = usuarioService.getCurrentUser();

        checkIfCategoryBelongsToUser(category,usuario);

        category.setUsuario(usuario);

        categoriaRepository.save(category);
    }

    private void checkIfCategoryBelongsToUser(Category category, Usuario usuario) {
       if(!categoriaRepository.findByUsuario(usuario).contains(category))
           throw new IllegalArgumentException("Category not belongs to user");
    }

    @Override
    public void delete(Long id) {
        verificaSeGastoExiste(id);
        categoriaRepository.deleteById(id);
    }

    @Override
    public CategoryDto findOne(Long id) {
        Category category = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NO_CATEGORY_FOUND_IN_ID, null));

        checkIfCategoryBelongsToUser(category,usuarioService.getCurrentUser());

        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public Category findBy(Long id) {
        return categoriaRepository.findByIdAndUsuario(id,usuarioService.getCurrentUser())
                .orElseThrow(() -> new ResourceNotFoundException(NO_CATEGORY_FOUND_IN_ID, null));
    }

    private void verificaSeGastoExiste(Long id) {
        if (!categoriaRepository.existsById(id))
            throw new ResourceNotFoundException(NO_CATEGORY_FOUND_IN_ID, null);
    }
}
