package br.com.springboot.controleorcamento.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoriaTest {

    Categoria categoria;

    @Before
    public void setUp(){
        categoria = new Categoria();
        categoria.setDescricao("Saúde");
        categoria.setTipo(Tipo.SAIDA);
        categoria.setId(1L);
    }

    @Test
    public void deveCompararDuasCategoriasComIdsIguaisEDizerQueSaoIguais(){
        Categoria categoriaComparacao = new Categoria();
        categoriaComparacao.setId(1L);

        assertEquals(categoriaComparacao, categoria);
    }

    @Test
    public void deveCompararDuasCategoriasComIdsDiferentesEDizerQueSaoDiferentes(){
        Categoria categoriaComparacao = new Categoria();
        categoriaComparacao.setDescricao("Saúde");
        categoriaComparacao.setTipo(Tipo.SAIDA);
        categoriaComparacao.setId(2L);

        assertNotEquals(categoriaComparacao,categoria);
    }

    @Test
    public void deveMostrarIdNoToString(){
         assertTrue(categoria.toString().contains("id"));
    }

}