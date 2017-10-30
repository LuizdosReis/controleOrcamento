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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springfox.documentation.schema.Model;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    CategoriaDto categoriaDto;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new CategoriaController(categoriaService);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("luiz henrique dandolini dos reis");
        usuario.setUsername("luiz.reis");

        categoriaDto = new CategoriaDto();
        categoriaDto.setDescricao("Carro");
        categoriaDto.setTipo(Tipo.SAIDA);
        categoriaDto.setId(1L);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void deveRetornaViewLista() throws Exception {


        when(categoriaService.findByUsuario(usuario))
                .thenReturn(Arrays.asList(categoriaDto));

        mockMvc.perform(get("/site/categorias"))
                .andExpect(status().isOk())
                .andExpect(view().name("categorias/lista"))
                .andExpect(model().attributeExists("categorias"));

        verify(categoriaService, times(1)).findByUsuario(usuario);

    }

    @Test
    @WithMockUser(value = "luiz.reis")
    public void deveSalvarNovaCategoria() throws Exception {
        CategoriaCreateDto categoriaCreateDto = new CategoriaCreateDto();
        categoriaCreateDto.setDescricao("Carro");
        categoriaCreateDto.setTipo(Tipo.SAIDA);


        when(categoriaService.save(categoriaCreateDto,usuario)).thenReturn(categoriaDto);

        mockMvc.perform(post("/site/categorias")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("tipo","SAIDA")
                .param("descricao","alguma descricao")
                )

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/site/categorias/1"));
    }
}