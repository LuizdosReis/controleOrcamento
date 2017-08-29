package br.com.springboot.controleorcamento.controleorcamento.helper;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import br.com.springboot.controleorcamento.controleorcamento.model.GastoCategorizado;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GastoHelper {

    public static Gasto CriaGasto(){
        List<GastoCategorizado> gastosCategorizado = new ArrayList<>();

        gastosCategorizado.add(new GastoCategorizado(new Categoria("Carro"), new BigDecimal("32.50")));

        return new Gasto("Gasolina", LocalDate.now(), gastosCategorizado);
    }
}
