package br.com.springboot.controleorcamento.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

@Entity
public class Conta extends AbstractEntity{

    @NotEmpty(message = "A descrição não pode ser vazia")
    private String descricao;

    @NotNull
    @Digits(fraction=2,message="O valor só pode conter dois digitos após a virgula",integer = 9)
    private BigDecimal saldo = new BigDecimal("0.00");

    @JsonIgnore
    @ManyToOne
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "conta")
    private Set<Despesa> despesas = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "conta")
    private Set<Receita> receitas = new HashSet<>();

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

    public void adicionaDespesa(Despesa despesa){
        if(despesa.isEfetivada())
            this.saldo = this.saldo.subtract(despesa.getValor());
        this.despesas.add(despesa);
    }

    public void removeDespesa(Despesa despesa){
        this.saldo = this.saldo.add(despesa.getValor());
        this.despesas.remove(despesa);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        usuario.setConta(this);
        this.usuario = usuario;
    }

    public void adicionaReceita(Receita receita){
        this.saldo = this.saldo.add(receita.getValor());
        receita.setConta(this);
        this.receitas.add(receita);
    }

    public void removeReceita(Receita receita){
        this.saldo = this.saldo.subtract(receita.getValor());
        receita.setConta(null);
        this.despesas.remove(receita);
    }

    public Set<Despesa> getDespesas() {
        return Collections.unmodifiableSet(despesas);
    }

    public Set<Receita> getReceitas() {
        return Collections.unmodifiableSet(receitas);
    }


}
