package br.com.springboot.controleorcamento.controleorcamento.repository;

import br.com.springboot.controleorcamento.controleorcamento.helper.GastoHelper;
import br.com.springboot.controleorcamento.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ContaRepositoryTest {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private GastoRepository gastoRepository;

    Conta conta;


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        conta = new Conta.Builder().descricao("bradesco").id(1L).saldo(new BigDecimal("12.00")).build();
    }

    @Test
    public void deveLancarExcecaoDeCriacaoDeContaSemDescricao(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("A descrição não pode ser vazia");

        conta.setDescricao("");

        contaRepository.save(conta);
    }

    @Test
    public void deveLancarExcecaoDeCriacaoDeContaComValorDeDigitosDepoisDaVirgulaInvalidos(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O valor só pode conter dois digitos após a virgula");

        conta = new Conta.Builder().descricao("bradesco").id(3L).saldo(new BigDecimal("12.000")).build();
        contaRepository.save(conta);
    }

    @Test
    public void deveCriarUmaConta(){
        contaRepository.save(conta);

        assertThat(conta.getId()).isNotNull();
        assertThat(conta.getDescricao()).isEqualTo("bradesco");
        assertThat(conta.getSaldo()).isEqualTo(new BigDecimal("12.00"));
    }


    @Test
    public void deveAdicionaUmGasto(){
        Gasto gasto = gastoRepository.save(GastoHelper.CriaGasto());

        conta.adicionaGasto(gasto);

        conta = contaRepository.save(conta);

        assertThat(conta.getId()).isNotNull();
        assertThat(conta.getDescricao()).isEqualTo("bradesco");
        assertThat(conta.getSaldo()).isEqualTo(new BigDecimal("-20.50"));
        assertThat(conta.getGastos().size()).isEqualTo(1);
        assertThat(conta.getGastos().get(0).getValor()).isEqualTo(new BigDecimal("32.50"));
    }

}