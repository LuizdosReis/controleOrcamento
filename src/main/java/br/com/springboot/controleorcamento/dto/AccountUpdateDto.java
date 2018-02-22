package br.com.springboot.controleorcamento.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateDto {

    @NotNull
    private Long id;
    private String description;
    private BigDecimal balance;
}
