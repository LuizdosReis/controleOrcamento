package br.com.springboot.controleorcamento.dto;

import br.com.springboot.controleorcamento.model.Type;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDto {

    private String description;
    private Type type;

}
