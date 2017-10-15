package br.com.springboot.controleorcamento.controleorcamento.model;

import br.com.springboot.controleorcamento.controleorcamento.converter.LocalDateAttributeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Receita extends AbstractEntity{

    @NotEmpty(message = "A descrição não pode ser vazia")
    private String descricao;

    @Digits(fraction=2,message="O valor só pode conter dois digitos após a virgula",integer = 9)
    @DecimalMin(message = "O Valor não pode ser negativo", value = "0.00", inclusive = false)
    private BigDecimal valor;

    private boolean efetivada;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate data;

    @ManyToOne
    private Conta conta;

    @NotNull
    @ManyToOne
    private Categoria categoria;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isEfetivada() {
        return efetivada;
    }

    public void setEfetivada(boolean efetivada) {
        this.efetivada = efetivada;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        conta.adicionaReceita(this);
        this.conta = conta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
