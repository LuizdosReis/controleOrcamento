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
    private BigDecimal saldo;

    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "usuario_conta", joinColumns = @JoinColumn(name = "conta_id"),
    inverseJoinColumns = @JoinColumn(name="usuario_id"))
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "conta")
    private Set<Despesa> gastos = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "conta")
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
        this.saldo = this.saldo.subtract(despesa.getValor());
        this.gastos.add(despesa);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void adicionaReceita(Receita receita){
        this.saldo = this.saldo.add(receita.getValor());
        this.receitas.add(receita);
    }

    public Set<Despesa> getGastos() {
        return Collections.unmodifiableSet(gastos);
    }

    public Set<Receita> getReceitas() {
        return Collections.unmodifiableSet(receitas);
    }


}
