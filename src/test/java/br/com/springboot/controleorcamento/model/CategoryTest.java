package br.com.springboot.controleorcamento.model;

import br.com.springboot.controleorcamento.helper.CategoryHelper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    Category category;

    @Before
    public void setUp(){
        category = CategoryHelper.buildCategory();
    }

    @Test
    public void deveCompararDuasCategoriasComIdsIguaisEDizerQueSaoIguais(){
        Category categoryComparacao = CategoryHelper.buildCategory();

        assertEquals(categoryComparacao, category);
    }

    @Test
    public void deveCompararDuasCategoriasComIdsDiferentesEDizerQueSaoDiferentes(){
        Category categoryComparacao = CategoryHelper.buildCategory();
        categoryComparacao.setId(2L);

        assertNotEquals(categoryComparacao, category);
    }

    @Test
    public void deveMostrarIdNoToString(){
         assertTrue(category.toString().contains("id"));
    }

}