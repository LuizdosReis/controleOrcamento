package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.dto.CategoriaCreateDto;
import br.com.springboot.controleorcamento.dto.CategoriaDto;
import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Tipo;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.CategoriaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoriaControllerTest {

    @Mock
    CategoriaService categoriaService;

    CategoriaController categoriaController;

    Usuario usuario;

    Categoria categoria;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoriaController = new CategoriaController(categoriaService);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("luiz henrique dandolini dos reis");
        usuario.setUsername("luiz.reis");

        categoria = new Categoria();
        categoria.setDescricao("Carro");
        categoria.setTipo(Tipo.SAIDA);
        categoria.setId(1L);

        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();
    }

    @Test
    public void deveRetornaViewLista() throws Exception {
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setDescricao("Carro");
        categoriaDto.setTipo(Tipo.SAIDA);
        categoriaDto.setId(1L);

        when(categoriaService.findAll())
                .thenReturn(Arrays.asList(categoriaDto));

        mockMvc.perform(get("/site/categorias"))
                .andExpect(status().isOk())
                .andExpect(view().name("categorias/lista"))
                .andExpect(model().attributeExists("categorias"));

        verify(categoriaService, times(1)).findAll();

    }

    @Test
    public void deveSalvarNovaCategoria() throws Exception {
        Categoria categoryParam = new Categoria();
        categoryParam.setDescricao("Carro");
        categoryParam.setTipo(Tipo.SAIDA);


        when(categoriaService.save(categoryParam)).thenReturn(this.categoria);

        mockMvc.perform(post("/site/categorias")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(user(usuario))
                .param("tipo", this.categoria.getTipo().toString())
                .param("descricao", this.categoria.getDescricao())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/site/categorias/1"));
    }

    @Test
    public void testGetNewCategoryForm() throws Exception {
        mockMvc.perform(get("/site/categorias/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("categorias/form"))
                .andExpect(model().attributeExists("categoria"))
                .andExpect(model().attributeExists("tipos"));
    }
}