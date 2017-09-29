package br.com.springboot.controleorcamento.controleorcamento.service;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.repository.CategoriaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoriaServiceImplTest {

    @MockBean
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CategoriaService categoriaServiceImpl;


    @Test
    public void save() throws Exception {
        Usuario luiz = new Usuario();
        luiz.setNome("Luiz Henrique");
        luiz.setUsername("luiz.reis");
        luiz.setPassword("123");

        Categoria carro = new Categoria();
        carro.setDescricao("Carro");

        when(categoriaRepository.save(carro)).thenReturn(carro);

        Categoria categoriaRetornada = categoriaServiceImpl.save(carro,luiz);

        assertThat(categoriaRetornada.getDescricao()).isEqualTo(carro.getDescricao());
        assertThat(categoriaRetornada.getUsuario()).isEqualTo(luiz);

    }

    @Test
    public void findByUsuario() throws Exception {
        Usuario luiz = new Usuario();
        luiz.setNome("Luiz Henrique");
        luiz.setUsername("luiz.reis");
        luiz.setPassword("123");

        Categoria carro = new Categoria();
        carro.setDescricao("Carro");

        when(categoriaRepository.findByUsuario(luiz,new PageRequest(0,20))).thenReturn(new PageImpl<>(Collections.singletonList(carro)));

        List<Categoria> categorias = categoriaServiceImpl.findByUsuario(luiz, new PageRequest(0,20)).getContent();

        assertThat(categorias.size()).isEqualTo(1);
        assertThat(categorias.get(0)).isEqualTo(carro);

    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void findOne() throws Exception {
    }

    @Test
    public void verificaSeCategoriasPertencemAoUsuario() throws Exception {
    }

}