package br.com.springboot.controleorcamento.controleorcamento.helper;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.controleorcamento.model.DespesaCategorizada;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GastoHelper {

    public static Despesa CriaGasto(){
        List<DespesaCategorizada> gastosCategorizado = new ArrayList<>();

        gastosCategorizado.add(new DespesaCategorizada(new Categoria("Carro"), new BigDecimal("32.50")));

        return new Despesa("Gasolina", LocalDate.now(), gastosCategorizado);
    }
}
