package br.com.springboot.controleorcamento.controleorcamento.dao;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import br.com.springboot.controleorcamento.controleorcamento.model.GastoCategorizado;
import br.com.springboot.controleorcamento.controleorcamento.repository.CategoriaRepository;
import br.com.springboot.controleorcamento.controleorcamento.repository.GastoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class GastoDaoTest {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private GastoDao gastoDao;


    @Test
    public void findGastoPorCategoria() throws Exception {
        List<GastoCategorizado> gastosCategorizados = new ArrayList<>();

        Categoria carro = new Categoria(1L,"Carro");

        Categoria moto = new Categoria(2L,"Moto");

        this.categoriaRepository.save(carro);

        this.categoriaRepository.save(moto);

        gastosCategorizados.add(new GastoCategorizado(carro, new BigDecimal("32.50")));

        gastosCategorizados.add(new GastoCategorizado(moto, new BigDecimal("11.50")));

        Gasto gasto = new Gasto("Gasolina", LocalDate.now(), gastosCategorizados);

        this.gastoRepository.save(gasto);

        List<Gasto> gastos = gastoDao.findGastoPorCategoria(carro.getDescricao());

        assertThat(gastos.get(0).getGastosCategorizados().size()).isEqualTo(1);
        assertTrue(gastos.get(0).getValor().equals(new BigDecimal("44.00")));
        assertTrue(gastos.get(0).getGastosCategorizados().get(0).getValor().equals(new BigDecimal("32.50")));
    }




}