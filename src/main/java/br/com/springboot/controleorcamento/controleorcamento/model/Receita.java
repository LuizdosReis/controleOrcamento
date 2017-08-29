package br.com.springboot.controleorcamento.controleorcamento.model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
public class Receita extends AbstractEntity{

    @NotEmpty(message = "A descrição não pode ser vazia")
    private String descrição;

    @Digits(fraction=2,message="O valor só pode conter dois digitos após a virgula",integer = 9)
    @DecimalMin(message = "O Valor não pode ser negativo", value = "0.00", inclusive = false)
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
