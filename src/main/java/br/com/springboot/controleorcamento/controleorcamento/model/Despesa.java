package br.com.springboot.controleorcamento.controleorcamento.model;

import br.com.springboot.controleorcamento.controleorcamento.converter.LocalDateAttributeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Despesa extends AbstractEntity {

    @NotEmpty(message = "A descrição não pode ser vazia")
    private String descricao;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate data;

    @Digits(fraction=2,message="O valor só pode conter dois digitos após a virgula",integer = 9)
    @DecimalMin(message = "O Valor não pode ser zerado ou negativo", value = "0.00", inclusive = false)
    private BigDecimal valor;

    @NotNull
    @ManyToOne
    @JoinTable(name = "categoria_despesa", joinColumns = @JoinColumn(name = "despesa_id"),
            inverseJoinColumns = @JoinColumn(name="categoria_id"))
    private Categoria categoria;

    @ManyToOne
    @JoinTable(name = "conta_despesa", joinColumns = @JoinColumn(name = "despesa_id"),
            inverseJoinColumns = @JoinColumn(name="conta_id"))
    private Conta conta;

    public Despesa(String descricao, LocalDate data, BigDecimal valor, Categoria categoria) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.categoria = categoria;
    }

    public Despesa(){
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
