package br.com.springboot.controleorcamento.dto;

import br.com.springboot.controleorcamento.model.Tipo;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDto {

    @NotEmpty
    private String descricao;

    @NotNull
    private Tipo tipo;

}
