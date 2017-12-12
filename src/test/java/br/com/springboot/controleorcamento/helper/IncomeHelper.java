package br.com.springboot.controleorcamento.helper;

import br.com.springboot.controleorcamento.dto.IncomeCreateDto;
import br.com.springboot.controleorcamento.dto.IncomeDto;
import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.Income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public class IncomeHelper {

    public static Income buildIncome(){
        return Income.builder()
                .description("Some description")
                .date(LocalDate.of(2017, Month.DECEMBER,5))
                .category(Category.builder().id(1L).build())
                .account(AccountHelper.buildAccount())
                .received(Boolean.FALSE)
                .value(BigDecimal.valueOf(32.50))
                .build();
    }

    public static IncomeDto buildIncomeDto(){
        return IncomeDto.builder()
                .description("Some description")
                .date(LocalDate.of(2017, Month.DECEMBER,5))
                .received(Boolean.TRUE)
                .value(new BigDecimal("32.50"))
                .build();
    }

    public static IncomeCreateDto buildIncomeCreateDto(){
        return IncomeCreateDto.builder()
                .income(buildIncomeDto())
                .accountId(1L)
                .categoryId(1L)
                .build();
    }
}

