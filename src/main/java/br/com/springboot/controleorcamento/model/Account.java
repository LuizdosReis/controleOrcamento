package br.com.springboot.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true,exclude = {"despesas","receitas","usuario","saldo","descricao"})
@ToString(callSuper = true,exclude = {"receitas","despesas"})
public class Account extends AbstractEntity{

    @Builder
    private Account(long id, String descricao,Usuario usuario, Set<Despesa> despesas,Set<Receita> receitas, BigDecimal saldo){
        super(id);
        this.descricao = descricao;
        this.usuario = usuario;
        this.despesas = despesas;
        this.receitas = receitas;
        this.saldo = saldo;
    }

    @NotEmpty(message = "A descrição não pode ser vazia")
    private String descricao;

    @NotNull
    @Digits(fraction=2,message="O valor só pode conter dois digitos após a virgula",integer = 9)
    private BigDecimal saldo;

    @JsonIgnore
    @ManyToOne
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "conta")
    @Setter(AccessLevel.NONE)
    private Set<Despesa> despesas;

    @JsonIgnore
    @OneToMany(mappedBy = "conta")
    @Setter(AccessLevel.NONE)
    private Set<Receita> receitas;

    public void adicionaDespesa(Despesa despesa){
        if(despesa.isEfetivada())
            this.saldo = this.saldo.subtract(despesa.getValor());
        this.despesas.add(despesa);
    }

    public void removeDespesa(Despesa despesa){
        this.saldo = this.saldo.add(despesa.getValor());
        this.despesas.remove(despesa);
    }

    public void adicionaReceita(Receita receita){
        this.saldo = this.saldo.add(receita.getValor());
        receita.setConta(this);
        this.receitas.add(receita);
    }

    public void removeReceita(Receita receita){
        this.saldo = this.saldo.subtract(receita.getValor());
        receita.setConta(null);
        this.receitas.remove(receita);
    }

    public Set<Despesa> getDespesas() {
        return Collections.unmodifiableSet(despesas);
    }

    public Set<Receita> getReceitas() {
        return Collections.unmodifiableSet(receitas);
    }


}
