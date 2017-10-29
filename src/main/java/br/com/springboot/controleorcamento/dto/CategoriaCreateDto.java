package br.com.springboot.controleorcamento.dto;

import br.com.springboot.controleorcamento.model.Tipo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoriaCreateDto {

    @NotNull
    private String descricao;

    @NotNull
    private Tipo tipo;

}
