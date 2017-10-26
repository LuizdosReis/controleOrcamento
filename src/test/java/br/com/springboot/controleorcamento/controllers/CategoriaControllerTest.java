package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Tipo;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.projections.CategoriaInfo;
import br.com.springboot.controleorcamento.service.CategoriaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springfox.documentation.schema.Model;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CategoriaControllerTest {

    @Mock
    CategoriaService categoriaService;

    @Mock
    Model model;

    CategoriaController controller;
    Usuario usuario;
    Categoria categoria;
    CategoriaInfo categoriaInfo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new CategoriaController(categoriaService, converter);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("luiz henrique dandolini dos reis");
        usuario.setUsername("luiz.reis");

        categoriaInfo = new CategoriaInfo() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getDescricao() {
                return "categoria";
            }

            @Override
            public Tipo getTipo() {
                return Tipo.SAIDA;
            }
        };
    }

    @Test
    public void deveRetornaViewLista() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(categoriaService.findByUsuario(usuario,null))
                .thenReturn(new PageImpl<>(Arrays.asList(categoriaInfo)));

        mockMvc.perform(get("/site/categorias"))
                .andExpect(status().isOk())
                .andExpect(view().name("categorias/lista"))
                .andExpect(model().attributeExists("categorias"));

    }
}