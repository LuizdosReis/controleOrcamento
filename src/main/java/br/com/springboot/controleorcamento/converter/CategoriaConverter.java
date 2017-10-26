package br.com.springboot.controleorcamento.converter;

import br.com.springboot.controleorcamento.dto.CategoriaDto;
import br.com.springboot.controleorcamento.model.Categoria;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriaConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CategoriaDto convertToDto(Categoria categoria){
        return modelMapper.map(categoria, CategoriaDto.class);
    }

    public Categoria convertToEntity(CategoriaDto categoriaDto){
        return modelMapper.map(categoriaDto,Categoria.class);
    }
}
