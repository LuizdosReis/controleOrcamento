package br.com.springboot.controleorcamento.repository;

import br.com.springboot.controleorcamento.helper.IncomeHelper;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.Income;
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
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IncomeRepositoryTest {

    @Autowired
    private IncomeRepository incomeRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Income income;

    @Before
    public void setUp(){
        income = IncomeHelper.buildIncome();
    }

    @Test
    public void shouldSaveIncome() {
        Income incomeSaved = incomeRepository.save(income);

        assertNotNull(incomeSaved);
        assertThat(incomeSaved.getCategory()).isEqualTo(Category.builder().id(1L).build());
        assertThat(incomeSaved.getAccount().getSaldo()).isEqualTo(new BigDecimal("12.50"));
        assertThat(incomeSaved.getDescription()).isEqualTo("Some description");

    }

    @Test
    public void SaveIncomeWithDescriptionEmptyShowThrowConstraintException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("The description can not be empty");

        income.setDescription("");
        incomeRepository.save(income);
    }

    @Test
    public void SaveIncomeWithValueZeroedShowThrowConstraintException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("The value can not be zeroed or negative");

        income.setValue(new BigDecimal("0.00"));
        incomeRepository.save(income);
    }

    @Test
    public void SaveIncomeWithValueNegativeShowThrowConstraintException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("The value can not be zeroed or negative");

        income.setValue(new BigDecimal("-10.00"));
        incomeRepository.save(income);
    }

    @Test
    public void SaveIncomeWithValueFractionWithMoreThanTwoDigitsShowThrowConstraintException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("The value can only contain two digits after the comma");

        income.setValue(new BigDecimal("10.000"));
        incomeRepository.save(income);
    }

    @Test
    public void SaveIncomeWithDateNullShowThrowConstraintException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("The date can not be null");

        income.setDate(null);

        incomeRepository.save(income);
    }

    @Test
    public void SaveIncomeWithAccountNullShowThrowConstraintException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("The account can not be null");

        income.setAccount(null);
        incomeRepository.save(income);
    }

    @Test
    public void SaveIncomeWithCategoryNullShowThrowConstraintException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("The category can not be null");

        income.setCategory(null);
        incomeRepository.save(income);
    }

    @Test
    public void shouldUpdateIncome() {
        income.setId(1L);
        income.setDescription("description update");
        incomeRepository.save(income);
        Optional<Income> incomeOptional = incomeRepository.findById(this.income.getId());

        assertTrue(incomeOptional.isPresent());

        assertThat(incomeOptional.get().getDescription()).isEqualTo(income.getDescription());
    }

    @Test
    public void shouldDeleteIncome() {
        incomeRepository.deleteById(1L);
        Optional<Income> incomeOptional = incomeRepository.findById(1L);

        assertFalse(incomeOptional.isPresent());

    }



}
