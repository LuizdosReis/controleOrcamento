package br.com.springboot.controleorcamento.controleorcamento.repository;

import br.com.springboot.controleorcamento.controleorcamento.helper.GastoHelper;
import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import br.com.springboot.controleorcamento.controleorcamento.model.GastoCategorizado;
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
    private GastoRepository gastoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void deveCriarGasto() {
        List<GastoCategorizado> gastosCategorizado = new ArrayList<>();

        gastosCategorizado.add(new GastoCategorizado(new Categoria("Carro"), new BigDecimal("32.50")));

        Gasto gasto = new Gasto("Gasolina", LocalDate.now(), gastosCategorizado);

        this.gastoRepository.save(gasto);

        assertThat(gasto.getId()).isNotNull();
        assertThat(gasto.getValor()).isEqualTo(new BigDecimal("32.50"));
    }

    @Test
    public void deveCriarGastoSetandoCategoriaPosteriormente() {
        Gasto gasto = GastoHelper.CriaGasto();

        gasto.adicionaGastoCategorizado(new GastoCategorizado(new Categoria("Moto"), new BigDecimal("32.50")));

        this.gastoRepository.save(gasto);

        assertThat(gasto.getId()).isNotNull();
        assertThat(gasto.getValor()).isEqualTo(new BigDecimal("65.00"));
    }

    @Test
    public void deveExcluirGasto() {
        Gasto gasto = GastoHelper.CriaGasto();

        this.gastoRepository.save(gasto);

        this.gastoRepository.delete(gasto);

        assertThat(gastoRepository.findOne(gasto.getId())).isNull();
    }

    @Test
    public void naoDeveInserirGastoCategorizadoAdicionadoPosteriormenteALista() {
        List<GastoCategorizado> gastosCategorizado = new ArrayList<>();

        gastosCategorizado.add(new GastoCategorizado(new Categoria("Carro"), new BigDecimal("32.50")));

        Gasto gasto = new Gasto("Gasolina", LocalDate.now(), gastosCategorizado);

        //Adiciona um gasto categorizado posteriormente que deve ser ignorado
        gastosCategorizado.add(new GastoCategorizado(new Categoria("Moto"), new BigDecimal("11.50")));

        this.gastoRepository.save(gasto);

        Gasto gastoEncontrado = gastoRepository.findOne(gasto.getId());

        assertTrue(gastoEncontrado.getValor().equals(new BigDecimal("32.50")));

    }


    @Test
    public void deveAdicionarGastoCategorizado() {
        List<GastoCategorizado> gastosCategorizado = new ArrayList<>();

        gastosCategorizado.add(new GastoCategorizado(new Categoria("Carro"), new BigDecimal("32.50")));

        Gasto gasto = new Gasto("Gasolina", LocalDate.now(), gastosCategorizado);

        this.gastoRepository.save(gasto);

        gasto.adicionaGastoCategorizado(new GastoCategorizado(new Categoria("Moto"), new BigDecimal("11.50")));

        gasto.setDescricao("Gasolina 2");

        Gasto gastoEncontrado = gastoRepository.findOne(gasto.getId());

        assertTrue(gastoEncontrado.getValor().equals(new BigDecimal("44.00")));
        assertThat(gastoEncontrado.getDescricao()).isEqualTo("Gasolina 2");

    }


    //@Test
    public void deveTrazerGastosPelaCategoria() {
        List<GastoCategorizado> gastosCategorizados = new ArrayList<>();

        Categoria carro = new Categoria(1L,"Carro");

        Categoria moto = new Categoria(2L,"Moto");

        this.categoriaRepository.save(carro);

        this.categoriaRepository.save(moto);

        gastosCategorizados.add(new GastoCategorizado(carro, new BigDecimal("32.50")));

        gastosCategorizados.add(new GastoCategorizado(moto, new BigDecimal("11.50")));

        Gasto gasto = new Gasto("Gasolina", LocalDate.now(), gastosCategorizados);

        this.gastoRepository.save(gasto);

        List<Gasto> gastos = gastoRepository.findByCategoria(carro, null).getContent();

        assertThat(gastos.get(0).getGastosCategorizados().size()).isEqualTo(1);
        assertTrue(gastos.get(0).getValor().equals(new BigDecimal("44.00")));
        assertTrue(gastos.get(0).getGastosCategorizados().get(0).getValor().equals(new BigDecimal("32.50")));

    }

    @Test
    public void quandoCriaNomeVazioDeveRetornaThrowConstrainViolationException(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("A descrição não pode ser vazia");

        List<GastoCategorizado> gastosCategorizado = new ArrayList<>();

        Categoria carro = new Categoria(1L,"Carro");

        this.categoriaRepository.save(carro);

        gastosCategorizado.add(new GastoCategorizado(carro, new BigDecimal("32.50")));

        Gasto gasto = new Gasto("", LocalDate.now(), gastosCategorizado);

        this.gastoRepository.save(gasto);
    }

    @Test
    public void quandoCriaUmGastoComValorZeradoDeveRetornaThrowConstrainViolationException(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O Valor não pode ser menor ou igual a 0.00");

        List<GastoCategorizado> gastosCategorizado = new ArrayList<>();

        Categoria carro = new Categoria(1L,"Carro");

        categoriaRepository.save(carro);

        gastosCategorizado.add(new GastoCategorizado(carro, new BigDecimal("0.00")));

        Gasto gasto = new Gasto("Gasolina", LocalDate.now(), gastosCategorizado);

        this.categoriaRepository.save(carro);

        this.gastoRepository.save(gasto);
    }

}




