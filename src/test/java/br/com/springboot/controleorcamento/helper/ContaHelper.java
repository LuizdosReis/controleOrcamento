package br.com.springboot.controleorcamento.helper;

import br.com.springboot.controleorcamento.model.Account;

import java.math.BigDecimal;

public class ContaHelper {

    public static Account criaConta(){
        Account conta = new Account();
        conta.setDescricao("bradesco");
        conta.setSaldo(new BigDecimal("12.00"));

        return conta;
    }
}
