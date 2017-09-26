package br.com.springboot.controleorcamento.controleorcamento.repository;

import br.com.springboot.controleorcamento.controleorcamento.helper.GastoHelper;
import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.controleorcamento.model.DespesaCategorizada;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GastosRepositoryTest {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void deveCriarGasto() {
        List<DespesaCategorizada> gastosCategorizado = new ArrayList<>();

        gastosCategorizado.add(new DespesaCategorizada(new Categoria("Carro"), new BigDecimal("32.50")));

        Despesa gasto = new Despesa("Gasolina", LocalDate.now(), gastosCategorizado);

        this.despesaRepository.save(gasto);

        assertThat(gasto.getId()).isNotNull();
        assertThat(gasto.getValor()).isEqualTo(new BigDecimal("32.50"));
    }

    @Test
    public void deveCriarGastoSetandoCategoriaPosteriormente() {
        Despesa gasto = GastoHelper.CriaGasto();

        gasto.adicionaGastoCategorizado(new DespesaCategorizada(new Categoria("Moto"), new BigDecimal("32.50")));

        this.despesaRepository.save(gasto);

        assertThat(gasto.getId()).isNotNull();
        assertThat(gasto.getValor()).isEqualTo(new BigDecimal("65.00"));
    }

    @Test
    public void deveExcluirGasto() {
        Despesa gasto = GastoHelper.CriaGasto();

        this.despesaRepository.save(gasto);

        this.despesaRepository.delete(gasto);

        assertThat(despesaRepository.findOne(gasto.getId())).isNull();
    }

    @Test
    public void naoDeveInserirGastoCategorizadoAdicionadoPosteriormenteALista() {
        List<DespesaCategorizada> gastosCategorizado = new ArrayList<>();

        gastosCategorizado.add(new DespesaCategorizada(new Categoria("Carro"), new BigDecimal("32.50")));

        Despesa gasto = new Despesa("Gasolina", LocalDate.now(), gastosCategorizado);

        //Adiciona um gasto categorizado posteriormente que deve ser ignorado
        gastosCategorizado.add(new DespesaCategorizada(new Categoria("Moto"), new BigDecimal("11.50")));

        this.despesaRepository.save(gasto);

        Despesa gastoEncontrado = despesaRepository.findOne(gasto.getId());

        assertTrue(gastoEncontrado.getValor().equals(new BigDecimal("32.50")));

    }


    @Test
    public void deveAdicionarGastoCategorizado() {
        List<DespesaCategorizada> gastosCategorizado = new ArrayList<>();

        gastosCategorizado.add(new DespesaCategorizada(new Categoria("Carro"), new BigDecimal("32.50")));

        Despesa gasto = new Despesa("Gasolina", LocalDate.now(), gastosCategorizado);

        this.despesaRepository.save(gasto);

        gasto.adicionaGastoCategorizado(new DespesaCategorizada(new Categoria("Moto"), new BigDecimal("11.50")));

        gasto.setDescricao("Gasolina 2");

        Despesa gastoEncontrado = despesaRepository.findOne(gasto.getId());

        assertTrue(gastoEncontrado.getValor().equals(new BigDecimal("44.00")));
        assertThat(gastoEncontrado.getDescricao()).isEqualTo("Gasolina 2");

    }


    @Test
    public void deveTrazerGastosPelaCategoria() {
        List<DespesaCategorizada> gastosCategorizados = new ArrayList<>();

        Categoria carro = new Categoria("Carro");

        Categoria moto = new Categoria("Moto");

        this.categoriaRepository.save(carro);

        this.categoriaRepository.save(moto);

        gastosCategorizados.add(new DespesaCategorizada(carro, new BigDecimal("32.50")));

        gastosCategorizados.add(new DespesaCategorizada(moto, new BigDecimal("11.50")));

        Despesa gasto = new Despesa("Gasolina", LocalDate.now(), gastosCategorizados);

        this.despesaRepository.save(gasto);

        List<Despesa> gastos = despesaRepository.findByDespesasCategorizadas(carro, null).getContent();

        assertThat(gastos.get(0).getDespesasCategorizadas().size()).isEqualTo(1);
        assertTrue(gastos.get(0).getValor().equals(new BigDecimal("44.00")));
        //assertTrue(gastos.get(0).getGastosCategorizados().get(0).getValor().equals(new BigDecimal("32.50")));

    }

    @Test
    public void quandoCriaNomeVazioDeveRetornaThrowConstrainViolationException(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("A descrição não pode ser vazia");

        List<DespesaCategorizada> gastosCategorizado = new ArrayList<>();

        Categoria carro = new Categoria(1L,"Carro");

        this.categoriaRepository.save(carro);

        gastosCategorizado.add(new DespesaCategorizada(carro, new BigDecimal("32.50")));

        Despesa gasto = new Despesa("", LocalDate.now(), gastosCategorizado);

        this.despesaRepository.save(gasto);
    }

    @Test
    public void quandoCriaUmGastoComValorZeradoDeveRetornaThrowConstrainViolationException(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O Valor não pode ser negativo");

        List<DespesaCategorizada> gastosCategorizado = new ArrayList<>();

        Categoria carro = new Categoria(1L,"Carro");

        categoriaRepository.save(carro);

        gastosCategorizado.add(new DespesaCategorizada(carro, new BigDecimal("0.00")));

        Despesa gasto = new Despesa("Gasolina", LocalDate.now(), gastosCategorizado);

        this.categoriaRepository.save(carro);

        this.despesaRepository.save(gasto);
    }

}




