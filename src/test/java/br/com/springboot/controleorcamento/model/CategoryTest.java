package br.com.springboot.controleorcamento.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    Category category;

    @Before
    public void setUp(){
        category = new Category();
        category.setDescricao("Saúde");
        category.setTipo(Tipo.SAIDA);
        category.setId(1L);
    }

    @Test
    public void deveCompararDuasCategoriasComIdsIguaisEDizerQueSaoIguais(){
        Category categoryComparacao = new Category();
        categoryComparacao.setId(1L);

        assertEquals(categoryComparacao, category);
    }

    @Test
    public void deveCompararDuasCategoriasComIdsDiferentesEDizerQueSaoDiferentes(){
        Category categoryComparacao = new Category();
        categoryComparacao.setDescricao("Saúde");
        categoryComparacao.setTipo(Tipo.SAIDA);
        categoryComparacao.setId(2L);

        assertNotEquals(categoryComparacao, category);
    }

    @Test
    public void deveMostrarIdNoToString(){
         assertTrue(category.toString().contains("id"));
    }

}