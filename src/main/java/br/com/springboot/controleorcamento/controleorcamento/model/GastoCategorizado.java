package br.com.springboot.controleorcamento.controleorcamento.model;

import org.hibernate.engine.internal.Cascade;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class GastoCategorizado extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Categoria categoria;

    @Digits(fraction=2,message="O valor so pode conter dois digitos após a virgula",integer = 9)
    @DecimalMin(value="0.00", inclusive=false)
    private BigDecimal valor;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
