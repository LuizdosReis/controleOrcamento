package br.com.springboot.controleorcamento.controleorcamento.repository;

import br.com.springboot.controleorcamento.controleorcamento.helper.ContaHelper;
import br.com.springboot.controleorcamento.controleorcamento.helper.DespesaHelper;
import br.com.springboot.controleorcamento.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;
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

    Conta conta;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        conta = ContaHelper.criaConta();
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

        conta.setSaldo(new BigDecimal("12.000"));
        contaRepository.save(conta);
    }

    @Test
    public void deveCriarUmaConta(){

        contaRepository.save(conta);

        assertThat(conta.getId()).isNotNull();
        assertThat(conta.getDescricao()).isEqualTo("bradesco");
        assertThat(conta.getSaldo()).isEqualTo(new BigDecimal("12.00"));
    }
}