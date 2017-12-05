package br.com.springboot.controleorcamento.helper;

import br.com.springboot.controleorcamento.dto.CategoryCreateDto;
import br.com.springboot.controleorcamento.dto.CategoryDto;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.Tipo;

public class CategoryHelper {

    public static Category buildCategory(){
        return Category.builder()
                .descricao("Carro")
                .tipo(Tipo.SAIDA)
                .id(1L)
                .build();
    }

    public static CategoryDto buildCategoryDto(){
        return CategoryDto.builder()
                .descricao("Carro")
                .tipo(Tipo.SAIDA)
                .id(1L)
                .build();
    }

    public static CategoryCreateDto builderCreateCategoryDto(){
        return CategoryCreateDto.builder()
                .descricao("Carro")
                .tipo(Tipo.SAIDA)
                .build();

    }
}
