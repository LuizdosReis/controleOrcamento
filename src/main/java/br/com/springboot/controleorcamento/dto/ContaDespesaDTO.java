package br.com.springboot.controleorcamento.dto;

import br.com.springboot.controleorcamento.model.Despesa;

public class ContaDespesaDTO {
    private Despesa despesa;
    private Long contaId;

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }
}
