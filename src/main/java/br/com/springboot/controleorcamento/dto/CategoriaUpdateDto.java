package br.com.springboot.controleorcamento.dto;

import br.com.springboot.controleorcamento.model.Tipo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoriaUpdateDto {

    @Id
    @NotNull
    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private Tipo tipo;
}
