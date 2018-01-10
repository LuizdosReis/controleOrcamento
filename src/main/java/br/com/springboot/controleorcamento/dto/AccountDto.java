package br.com.springboot.controleorcamento.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;
    private String descricao;
    private BigDecimal saldo;

}
