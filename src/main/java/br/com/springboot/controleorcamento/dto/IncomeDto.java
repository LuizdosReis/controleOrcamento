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
public class IncomeDto {

    @NotEmpty(message = "A descrição não pode ser vazia")
    private String description;

    @Digits(fraction=2,message="O valor só pode conter dois digitos após a virgula",integer = 9)
    @DecimalMin(message = "O Valor não pode ser negativo", value = "0.00", inclusive = false)
    private BigDecimal value;

    private boolean received;

    @NotNull(message = "The date can not be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
}
