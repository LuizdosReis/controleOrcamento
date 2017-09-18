package br.com.springboot.controleorcamento.controleorcamento.dto;

import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;

public class ContaGastoDTO {
    private Despesa gasto;
    private Long contaId;

    public Despesa getGasto() {
        return gasto;
    }

    public void setGasto(Despesa gasto) {
        this.gasto = gasto;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }
}
