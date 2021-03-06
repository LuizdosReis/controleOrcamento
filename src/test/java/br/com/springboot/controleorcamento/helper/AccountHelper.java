package br.com.springboot.controleorcamento.helper;

import br.com.springboot.controleorcamento.dto.AccountCreateDto;
import br.com.springboot.controleorcamento.dto.AccountDto;
import br.com.springboot.controleorcamento.model.Account;

import java.math.BigDecimal;

public class AccountHelper {

    public static Account buildAccount(){
        return Account.builder()
                .description("Banco do Brasil")
                .balance(new BigDecimal("12.50"))
                .build();
    }

    public static AccountDto buildAccountDto(){
        return AccountDto.builder()
                .description("Sandanter")
                .balance(new BigDecimal("0.00"))
                .id(1L)
                .build();
    }

    public static AccountCreateDto buildCreateAccountDto(){
        return AccountCreateDto.builder()
                .description("Sandanter")
                .balance(new BigDecimal("0.00"))
                .build();
    }




}
