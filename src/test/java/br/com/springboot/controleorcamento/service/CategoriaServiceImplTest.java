package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.repository.CategoriaRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    private CategoriaService categoriaService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoriaService = new CategoriaServiceImpl(categoriaRepository);
    }

    @Test
    public void deveSalvarCategoria() throws Exception {
        Usuario luiz = new Usuario();
        luiz.setNome("Luiz Henrique");
        luiz.setUsername("luiz.reis");
        luiz.setPassword("123");

        Categoria carro = new Categoria();
        carro.setDescricao("Carro");

        when(categoriaRepository.save(carro)).thenReturn(carro);

        Categoria categoriaRetornada = categoriaService.save(carro,luiz);

        assertThat(categoriaRetornada.getDescricao()).isEqualTo(carro.getDescricao());
        assertThat(categoriaRetornada.getUsuario()).isEqualTo(luiz);
        verify(categoriaRepository,times(1)).save(carro);
    }

    @Test
    public void naoDeveSalvarCategoriaSemUsuario() throws Exception {
        thrown.expect(ConstraintViolationException.class);

        Categoria carro = new Categoria();
        carro.setDescricao("Carro");

        when(categoriaRepository.save(carro)).thenThrow(ConstraintViolationException.class);

        categoriaService.save(carro,null);
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

        List<Categoria> categorias = categoriaService.findByUsuario(luiz, new PageRequest(0,20)).getContent();

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