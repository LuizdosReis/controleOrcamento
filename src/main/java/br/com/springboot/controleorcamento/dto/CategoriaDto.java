package br.com.springboot.controleorcamento.dto;

import br.com.springboot.controleorcamento.model.Tipo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDto {

    private Long id;
    private String descricao;
    private Tipo tipo;
}
