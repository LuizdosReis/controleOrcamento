package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.ControleOrcamentoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControleOrcamentoApplication.class)
public class ExpenseServiceImplTest {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private AccountService accountService;


//    @Test
//    public void save() throws Exception {
//        Expense despesa = DespesaHelper.criaDespesa();
//
//        when(accountService.findOne(despesa.getConta().getId())).thenReturn(despesa.getConta());
//
//        expenseService.save(despesa);
//    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void getById() throws Exception {
    }

    @Test
    public void findByCategoria() throws Exception {
    }

    @Test
    public void findByDataBetween() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

}