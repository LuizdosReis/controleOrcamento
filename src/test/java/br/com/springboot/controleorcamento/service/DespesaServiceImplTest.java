package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.ControleOrcamentoApplication;
import br.com.springboot.controleorcamento.helper.DespesaHelper;
import br.com.springboot.controleorcamento.model.Despesa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControleOrcamentoApplication.class)
public class DespesaServiceImplTest {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private ContaService contaService;


//    @Test
//    public void save() throws Exception {
//        Despesa despesa = DespesaHelper.criaDespesa();
//
//        when(contaService.findOne(despesa.getConta().getId())).thenReturn(despesa.getConta());
//
//        despesaService.save(despesa);
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