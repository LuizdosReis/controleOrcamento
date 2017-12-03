package br.com.springboot.controleorcamento.dto;

import br.com.springboot.controleorcamento.model.Tipo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoriaUpdateDto {
    
    @NotNull
    private Long id;

    @NotEmpty
    private String descricao;

    @NotNull
    private Tipo tipo;
}
