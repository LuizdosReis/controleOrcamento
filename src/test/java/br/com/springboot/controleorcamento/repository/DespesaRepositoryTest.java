package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.helper.DespesaHelper;
import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Despesa;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DespesaRepositoryTest {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void name() throws Exception {

    }

    //    @Test
//    public void deveCriarGasto() {
//        Despesa despesa = DespesaHelper.criaDespesa();
//
//        despesa = this.despesaRepository.save(despesa);
//
//        assertThat(despesa.getId()).isNotNull();
//        assertThat(despesa.getValor()).isEqualTo(new BigDecimal("12.50"));
//        assertThat(despesa.getCategoria().getDescricao()).isEqualTo("Carro");
//        assertThat(despesa.getConta().getSaldo()).isEqualTo(new BigDecimal("-12.50"));
//
//    }
//
//    @Test
//    public void deveExcluirGasto() {
//        Despesa gasto = DespesaHelper.criaDespesa();
//
//        this.despesaRepository.save(gasto);
//
//        this.despesaRepository.delete(gasto);
//
//        assertThat(despesaRepository.findById(gasto.getId())).isNull();
//    }
//
//    @Test
//    public void deveTrazerDespesasPelaCategoria() {
//        //Despesa despesa = DespesaHelper.criaDespesa();
//
//
//        Categoria categoria = new Categoria();
//        categoria.setId(1L);
//
//
//
//
//        Page<Despesa> despesas = despesaRepository.findByCategoria(categoria,new PageRequest(0, 20));
//
//        System.out.println(despesas);
//
//        assertThat(despesas.getContent().size()).isEqualTo(1);
//    }
//
//    @Test
//    public void quandoCriaNomeVazioDeveRetornaThrowConstrainViolationException(){
//        thrown.expect(ConstraintViolationException.class);
//        thrown.expectMessage("A descrição não pode ser vazia");
//
//        Despesa despesa = DespesaHelper.criaDespesa();
//        despesa.setDescricao("");
//
//        this.despesaRepository.save(despesa);
//    }
//
//    @Test
//    public void quandoCriaUmGastoComValorZeradoDeveRetornaThrowConstrainViolationException(){
//        thrown.expect(ConstraintViolationException.class);
//        thrown.expectMessage("O Valor não pode ser zerado ou negativo");
//
//        Despesa despesa = DespesaHelper.CriaDespesaComValorZerado();
//
//        this.despesaRepository.save(despesa);
//    }
//
}




