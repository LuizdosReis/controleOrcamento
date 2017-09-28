package br.com.springboot.controleorcamento.controleorcamento.helper;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.controleorcamento.model.DespesaCategorizada;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DespesaHelper {

    public static Despesa CriaDespesa(){
        Set<DespesaCategorizada> despesasCategorizadas = new HashSet<>();

        Categoria carro = new Categoria("Carro");
        Categoria gasolina = new Categoria("Gasolina");

        DespesaCategorizada despesaCarro = new DespesaCategorizada();

        despesaCarro.setCategoria(carro);
        despesaCarro.setValor(new BigDecimal("12.50"));

        DespesaCategorizada despesaGasolina = new DespesaCategorizada();
        despesaGasolina.setCategoria(gasolina);
        despesaGasolina.setValor(new BigDecimal("12.50"));

        despesasCategorizadas.add(despesaCarro);
        despesasCategorizadas.add(despesaGasolina);

        Conta bancoDoBrasil = new Conta();
        bancoDoBrasil.setDescricao("banco do brasil");
        bancoDoBrasil.setSaldo(new BigDecimal("0.00"));

        Despesa despesa = new Despesa();

        despesa.setDescricao("Gasolina e oleo");
        despesa.setConta(bancoDoBrasil);
        despesa.setDespesasCategorizadas(despesasCategorizadas);

        return despesa;
    }
}
