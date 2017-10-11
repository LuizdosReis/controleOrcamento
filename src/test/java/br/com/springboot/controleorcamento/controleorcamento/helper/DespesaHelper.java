package br.com.springboot.controleorcamento.controleorcamento.helper;

import br.com.springboot.controleorcamento.controleorcamento.model.*;
import br.com.springboot.controleorcamento.controleorcamento.service.CategoriaService;
import br.com.springboot.controleorcamento.controleorcamento.service.ContaService;
import br.com.springboot.controleorcamento.controleorcamento.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DespesaHelper {

    public static Despesa criaDespesa(){

        Usuario luiz = new Usuario();
        luiz.setUsername("luiz.reis");
        luiz.setNome("luiz henrique dandolini dos reis ");
        luiz.setPassword("123");

        Categoria carro = new Categoria("Carro");

        Conta bancoDoBrasil = new Conta();
        bancoDoBrasil.setDescricao("banco do brasil");
        bancoDoBrasil.setSaldo(new BigDecimal("0.00"));


        Despesa despesa = new Despesa();
        despesa.setDescricao("Gasolina e oleo");
        despesa.setConta(bancoDoBrasil);
        despesa.setCategoria(carro);
        despesa.setValor(new BigDecimal("12.50"));
        despesa.setData(LocalDate.parse("15/01/2017", DateTimeFormatter.ofPattern("dd/MM/yyy")));

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
