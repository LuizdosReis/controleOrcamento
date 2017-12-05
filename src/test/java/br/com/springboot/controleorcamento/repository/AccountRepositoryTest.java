package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.helper.ContaHelper;
import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Despesa;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    Account account;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        account = ContaHelper.criaConta();
    }

    @Test
    public void deveLancarExcecaoDeCriacaoDeContaSemDescricao(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("A descrição não pode ser vazia");

        account.setDescricao("");

        accountRepository.save(account);
    }

    @Test
    public void deveLancarExcecaoDeCriacaoDeContaComValorDeDigitosDepoisDaVirgulaInvalidos(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O valor só pode conter dois digitos após a virgula");

        account.setSaldo(new BigDecimal("12.000"));
        accountRepository.save(account);
    }

    @Test
    public void deveCriarUmaConta(){

        accountRepository.save(account);

        assertThat(account.getId()).isNotNull();
        assertThat(account.getDescricao()).isEqualTo("bradesco");
        assertThat(account.getSaldo()).isEqualTo(new BigDecimal("12.00"));
    }

    @Test
    public void deveAtualizarUmaConta(){

        Account conta = accountRepository.findById(1L).get();

        conta.setDescricao("Account Atualizada");

        Account contaRetornada = accountRepository.save(conta);

        assertThat(contaRetornada.getDescricao()).isEqualTo(conta.getDescricao());

    }

    @Test
    public void deveLancarExcecaoParaExcluirContaComGastosCadastrados(){


        accountRepository.deleteById(1L);

        Optional<Account> conta = accountRepository.findById(1L);

        System.out.println(conta);

        Optional<Despesa> despesa = despesaRepository.findById(1L);

        System.out.println(despesa);


    }
}