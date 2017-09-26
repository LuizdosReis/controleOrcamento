package br.com.springboot.controleorcamento.controleorcamento.dao;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.controleorcamento.model.DespesaCategorizada;
import br.com.springboot.controleorcamento.controleorcamento.repository.CategoriaRepository;
import br.com.springboot.controleorcamento.controleorcamento.repository.DespesaRepository;
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
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
public class DespesaDaoTest {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private GastoDao gastoDao;


    @Test
    public void findGastoPorCategoria() throws Exception {
        List<DespesaCategorizada> gastosCategorizados = new ArrayList<>();

        Categoria carro = new Categoria(1L,"Carro");

        Categoria moto = new Categoria(2L,"Moto");

        this.categoriaRepository.save(carro);

        this.categoriaRepository.save(moto);

        gastosCategorizados.add(new DespesaCategorizada(carro, new BigDecimal("32.50")));

        gastosCategorizados.add(new DespesaCategorizada(moto, new BigDecimal("11.50")));

        this.despesaRepository.save(new Despesa("Gasolina", LocalDate.now(), gastosCategorizados));

        List<Despesa> gastos = gastoDao.findGastoPorCategoria(carro.getDescricao());

        assertThat(gastos.get(0).getDespesasCategorizadas().size()).isEqualTo(1);
        assertTrue(gastos.get(0).getValor().equals(new BigDecimal("44.00")));

        BigDecimal soma = gastos.get(0).getDespesasCategorizadas()
                .stream()
                .map(DespesaCategorizada::getValor)
                .reduce(new BigDecimal("0.00"), (a, b) -> a = a.add(b));

        assertTrue(soma.equals(new BigDecimal("32.50")));
    }




}