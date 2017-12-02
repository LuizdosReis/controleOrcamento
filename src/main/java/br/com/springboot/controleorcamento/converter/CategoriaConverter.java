package br.com.springboot.controleorcamento.converter;

import br.com.springboot.controleorcamento.dto.CategoryDto;
import br.com.springboot.controleorcamento.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CategoriaConverter {

    @Autowired
    private ModelMapper iModelMapper;

    private static ModelMapper modelMapper;

    @PostConstruct
    private void setUp() {
        this.modelMapper = iModelMapper;
    }

    public static CategoryDto convertToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public static Category convertToEntity(CategoryDto categoryDto) {
         return modelMapper.map(categoryDto,Category.class);
    }
}
