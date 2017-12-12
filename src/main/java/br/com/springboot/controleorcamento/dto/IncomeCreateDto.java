package br.com.springboot.controleorcamento.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeCreateDto {

    @NotNull
    private IncomeDto income;

    @NotNull
    private Long accountId;

    @NotNull
    private Long categoryId;


}
