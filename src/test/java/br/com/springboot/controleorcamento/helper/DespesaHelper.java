package br.com.springboot.controleorcamento.helper;

import br.com.springboot.controleorcamento.model.*;
import br.com.springboot.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DespesaHelper {

    public static Despesa criaDespesa(){

        Usuario luiz = new Usuario();
        luiz.setUsername("luiz.reis");
        luiz.setNome("luiz henrique dandolini dos reis ");
        luiz.setPassword("123");

        Categoria carro = new Categoria();
        carro.setId(1L);

        Conta bancoDoBrasil = new Conta();
        bancoDoBrasil.setId(1L);


        Despesa despesa = new Despesa();
        despesa.setDescricao("Gasolina e oleo");
        despesa.setCategoria(carro);
        despesa.setValor(new BigDecimal("12.50"));
        despesa.setData(LocalDate.parse("15/01/2017", DateTimeFormatter.ofPattern("dd/MM/yyy")));
        despesa.setConta(bancoDoBrasil);

        return despesa;
    }

    public static Despesa CriaDespesaComValorZerado(){
        Conta bancoDoBrasil = new Conta();
        bancoDoBrasil.setDescricao("banco do brasil");
        bancoDoBrasil.setSaldo(new BigDecimal("0.00"));

        Despesa despesa = new Despesa();
        despesa.setDescricao("Gasolina e oleo");
        despesa.setConta(bancoDoBrasil);
        despesa.setData(LocalDate.parse("15/01/2017", DateTimeFormatter.ofPattern("dd/MM/yyy")));

        return despesa;
    }
}
