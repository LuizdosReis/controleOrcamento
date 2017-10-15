package br.com.springboot.controleorcamento.helper;

import br.com.springboot.controleorcamento.model.Conta;

import java.math.BigDecimal;

public class ContaHelper {

    public static Conta criaConta(){
        Conta conta = new Conta();
        conta.setDescricao("bradesco");
        conta.setSaldo(new BigDecimal("12.00"));

        return conta;
    }
}
