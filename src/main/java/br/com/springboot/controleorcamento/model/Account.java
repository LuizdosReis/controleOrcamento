package br.com.springboot.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"despesas", "incomes", "usuario", "saldo", "descricao"})
@ToString(callSuper = true, exclude = {"incomes", "despesas"})
public class Account extends AbstractEntity {

    @NotBlank(message = "A descrição não pode ser vazia")
    private String descricao;
    @NotNull
    @Digits(fraction = 2, integer = 9)
    private BigDecimal saldo;
    @JsonIgnore
    @ManyToOne
    private Usuario usuario;
    @JsonIgnore
    @OneToMany(mappedBy = "conta")
    @Setter(AccessLevel.NONE)
    private Set<Despesa> despesas;
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    @Setter(AccessLevel.NONE)
    private Set<Income> incomes;

    @Builder
    private Account(long id, String descricao, Usuario usuario, Set<Despesa> despesas, Set<Income> incomes, BigDecimal saldo) {
        super(id);
        this.descricao = descricao;
        this.usuario = usuario;
        this.despesas = despesas;
        this.incomes = incomes;
        this.saldo = saldo;
    }

    public void adicionaDespesa(Despesa despesa) {
        if (despesa.isEfetivada())
            this.saldo = this.saldo.subtract(despesa.getValor());
        this.despesas.add(despesa);
    }

    public void removeDespesa(Despesa despesa) {
        this.saldo = this.saldo.add(despesa.getValor());
        this.despesas.remove(despesa);
    }

    public void addIncome(Income income) {
        if (income.isReceived())
            this.saldo = this.saldo.add(income.getValue());
        income.setAccount(this);
        this.incomes.add(income);
    }

    public void removeIncome(Income income) {
        if (income.isReceived())
            this.saldo = this.saldo.subtract(income.getValue());
        income.setAccount(null);
        this.incomes.remove(income);
    }

    

}
