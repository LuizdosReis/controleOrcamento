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

    @NotNull(message = "Income not be null")
    @Valid
    private IncomeDto income;

    @NotNull(message = "AccountId not be null")
    private Long accountId;

    @NotNull(message = "CategoryId not be null")
    private Long categoryId;


}
