package br.com.springboot.controleorcamento.controleorcamento.repository;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import org.assertj.core.api.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoriaRepositoryTest {

    @Autowired
    public CategoriaRepository categoriaRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void deveTrazerListaVaziaQuandoNaoHouverCategoria(){
        Iterable<Categoria> todas = categoriaRepository.findAll();

        int i = 0;
        Iterator<Categoria> iterator = todas.iterator();

        while(iterator.hasNext())
            i++;

        assertThat(i).isEqualTo(0);
    }


}