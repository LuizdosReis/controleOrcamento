package br.com.springboot.controleorcamento.dto;


import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeCreateDto {

    private IncomeDto income;
    private Long accountId;
    private Long categoryId;


}
