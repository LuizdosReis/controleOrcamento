package br.com.springboot.controleorcamento.controleorcamento.repository;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import br.com.springboot.controleorcamento.controleorcamento.model.GastoCategorizado;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GastosRepositoryTest {

    @Autowired
    private GastoRepository gastoRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void deveCriarGasto(){
        List<GastoCategorizado> gastosCategorizado = new ArrayList<>();

        gastosCategorizado.add(new GastoCategorizado(new Categoria("Carro"),new BigDecimal("32.50")));

        Gasto gasto = new Gasto("Gasolina", LocalDate.now(),new BigDecimal("32.50"),gastosCategorizado);

        this.gastoRepository.save(gasto);

        Assertions.assertThat(gasto.getId()).isNotNull();



    }
}




