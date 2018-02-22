package br.com.springboot.controleorcamento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"expenses", "incomes", "user", "balance", "description"})
@ToString(callSuper = true, exclude = {"incomes", "expenses"})
public class Account extends AbstractEntity {

    @NotBlank(message = "the description not be empty")
    private String description;
    @NotNull
    @Digits(fraction = 2, integer = 9)
    private BigDecimal balance;
    @JsonIgnore
    @ManyToOne
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    @Setter(AccessLevel.NONE)
    private Set<Expense> expenses;
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    @Setter(AccessLevel.NONE)
    private Set<Income> incomes;

    @Builder
    public Account(Long id,String description, BigDecimal balance, User user, Set<Expense> expenses, Set<Income> incomes) {
        super(id);
        this.description = description;
        this.balance = balance;
        this.user = user;
        this.expenses = expenses;
        this.incomes = incomes;
    }

    public void add(Expense expense) {
        if (expense.isEffected())
            this.balance = this.balance.subtract(expense.getValue());
        this.expenses.add(expense);
    }

    public void remove(Expense expense) {
        if (expense.isEffected())
            this.balance = this.balance.add(expense.getValue());
        this.expenses.remove(expense);
    }

    public void addIncome(Income income) {
        if (income.isReceived())
            this.balance = this.balance.add(income.getValue());
        income.setAccount(this);
        this.incomes.add(income);
    }

    public void removeIncome(Income income) {
        if (income.isReceived())
            this.balance = this.balance.subtract(income.getValue());
        income.setAccount(null);
        this.incomes.remove(income);
    }

    

}
