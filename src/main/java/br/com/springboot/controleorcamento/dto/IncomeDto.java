package br.com.springboot.controleorcamento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class IncomeDto {

    @NotBlank(message = "The description not be blank")
    private String description;

    @NotNull(message = "The value can not be null")
    @Digits(fraction=2,integer = 9,message="The value can only contain two digits after the comma")
    @Min(value = 0, message = "The value should be greater then zero")
    private BigDecimal value;

    private boolean received;

    @NotNull(message = "The date can not be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
}
