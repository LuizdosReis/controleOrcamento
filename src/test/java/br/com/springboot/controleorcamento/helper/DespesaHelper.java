package br.com.springboot.controleorcamento.helper;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.Expense;
import br.com.springboot.controleorcamento.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DespesaHelper {

    public static Expense criaDespesa(){

        User luiz = new User();
        luiz.setUsername("luiz.reis");
        luiz.setName("luiz henrique dandolini dos reis ");
        luiz.setPassword("123");

        Category carro = CategoryHelper.buildCategory();

        Account bancoDoBrasil = new Account();
        bancoDoBrasil.setId(1L);

        Expense expense = new Expense();
        expense.setDescription("Gasolina e oleo");
        expense.setCategory(carro);
        expense.setValue(new BigDecimal("12.50"));
        expense.setDate(LocalDate.parse("15/01/2017", DateTimeFormatter.ofPattern("dd/MM/yyy")));
        expense.setAccount(bancoDoBrasil);

        return expense;
    }

    public static Expense CreateExpenseWithValueZero(){
        Account bancoDoBrasil = new Account();
        bancoDoBrasil.setDescription("banco do brasil");
        bancoDoBrasil.setBalance(new BigDecimal("0.00"));

        Expense expense = new Expense();
        expense.setDescription("Gasolina e oleo");
        expense.setAccount(bancoDoBrasil);
        expense.setDate(LocalDate.parse("15/01/2017", DateTimeFormatter.ofPattern("dd/MM/yyy")));

        return expense;
    }
}
