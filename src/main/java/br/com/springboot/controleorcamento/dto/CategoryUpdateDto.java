package br.com.springboot.controleorcamento.dto;

import br.com.springboot.controleorcamento.model.Type;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDto {
    
    @NotNull
    private Long id;
    private String description;
    private Type type;
}
