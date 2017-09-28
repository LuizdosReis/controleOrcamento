package br.com.springboot.controleorcamento.controleorcamento.service;

import br.com.springboot.controleorcamento.controleorcamento.helper.DespesaHelper;
import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DespesaServiceImplTest {

    @Autowired
    private DespesaService despesaService;


    @Test
    public void save() throws Exception {
        Despesa despesa = DespesaHelper.CriaDespesa();

        despesaService.save(despesa);
    }

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