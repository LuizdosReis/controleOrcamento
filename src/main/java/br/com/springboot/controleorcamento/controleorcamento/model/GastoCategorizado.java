package br.com.springboot.controleorcamento.controleorcamento.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
public class GastoCategorizado extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;

    @Digits(fraction=2,message="O valor so pode conter dois digitos após a virgula",integer = 9)
    @DecimalMin(message = "O Valor não pode ser negativo", value = "0.00", inclusive = false)
    private BigDecimal valor;

    public GastoCategorizado(Categoria categoria, BigDecimal valor) {
        this.categoria = categoria;
        this.valor = valor;
    }

    public GastoCategorizado() {
    }

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
