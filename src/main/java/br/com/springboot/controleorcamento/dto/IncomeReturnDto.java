package br.com.springboot.controleorcamento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class IncomeReturnDto {

    @NotNull
    private Long id;
    private String description;
    private BigDecimal value;
    private boolean received;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
}
