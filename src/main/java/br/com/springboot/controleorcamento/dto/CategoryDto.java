package br.com.springboot.controleorcamento.dto;

import br.com.springboot.controleorcamento.model.Tipo;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String descricao;
    private Tipo tipo;
}
