package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.dto.CategoryUpdateDto;
import br.com.springboot.controleorcamento.dto.CategoryCreateDto;
import br.com.springboot.controleorcamento.dto.CategoryDto;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.User;
import br.com.springboot.controleorcamento.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserService userService, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto save(CategoryCreateDto categoriaDto) {
        log.debug("CategoryService - save");

        Category category = modelMapper.map(categoriaDto,Category.class);

        category.setUser(userService.getCurrentUser());
        category = categoryRepository.save(category);
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public Category save(Category category) {
        log.debug("CategoryService - save");

        category.setUser(userService.getCurrentUser());

        return categoryRepository.save(category);
    }

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        log.debug("CategoryService - findAll");
        Page<Category> page = categoryRepository.findByUser(userService.getCurrentUser(), pageable);

        List<CategoryDto> categoryDtos = page.getContent()
                .stream()
                .map(c -> modelMapper.map(c,CategoryDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(categoryDtos, pageable, page.getTotalElements());
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findByUser(userService.getCurrentUser());

        return categories.stream()
                .map(categoria -> modelMapper.map(categoria,CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(CategoryUpdateDto categoriaDto) {
        Category category = modelMapper.map(categoriaDto, Category.class);

        verificaSeGastoExiste(category.getId());

        User usuario = userService.getCurrentUser();

        checkIfCategoryBelongsToUser(category,usuario);

        category.setUser(usuario);

        categoryRepository.save(category);
    }

    private void checkIfCategoryBelongsToUser(Category category, User usuario) {
       if(!categoryRepository.findByUser(usuario).contains(category))
           throw new IllegalArgumentException("Category not belongs to user");
    }

    @Override
    public void delete(Long id) {
        verificaSeGastoExiste(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto findOne(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NO_CATEGORY_FOUND_IN_ID, null));

        checkIfCategoryBelongsToUser(category, userService.getCurrentUser());

        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public Category findBy(Long id) {
        return categoryRepository.findByIdAndUser(id, userService.getCurrentUser())
                .orElseThrow(() -> new ResourceNotFoundException(NO_CATEGORY_FOUND_IN_ID, null));
    }

    private void verificaSeGastoExiste(Long id) {
        if (!categoryRepository.existsById(id))
            throw new ResourceNotFoundException(NO_CATEGORY_FOUND_IN_ID, null);
    }
}
