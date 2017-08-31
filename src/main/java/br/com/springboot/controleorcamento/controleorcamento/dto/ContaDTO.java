package br.com.springboot.controleorcamento.controleorcamento.dto;

import java.math.BigDecimal;

public class ContaDTO {
    private String descricao;
    private BigDecimal saldo;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
