package br.com.springboot.controleorcamento.controleorcamento.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Receita extends AbstractEntity{

    private String descrição;

    private BigDecimal valor;

    private Boolean efetivada;

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean getEfetivada() {
        return efetivada;
    }

    public void setEfetivada(Boolean efetivada) {
        this.efetivada = efetivada;
    }
}
