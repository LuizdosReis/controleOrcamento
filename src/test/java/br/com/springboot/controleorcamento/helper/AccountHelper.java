package br.com.springboot.controleorcamento.helper;

import br.com.springboot.controleorcamento.dto.AccountCreateDto;
import br.com.springboot.controleorcamento.dto.AccountDto;
import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Usuario;

import java.math.BigDecimal;

public class AccountHelper {

    public static Account buildAccount(){
        return Account.builder()
                .descricao("Banco do Brasil")
                .saldo(new BigDecimal("12.50"))
                .id(1L)
                .build();
    }

    public static AccountDto buildAccountDto(){
        return AccountDto.builder()
                .descricao("Sandanter")
                .saldo(new BigDecimal("0.00"))
                .id(1L)
                .build();
    }

    public static AccountCreateDto buildCreateAccountDto(){
        return AccountCreateDto.builder()
                .descricao("Sandanter")
                .saldo(new BigDecimal("0.00"))
                .build();
    }




}
