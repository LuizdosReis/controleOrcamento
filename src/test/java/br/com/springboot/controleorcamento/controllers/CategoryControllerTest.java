package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.dto.CategoriaDto;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.Tipo;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.CategoryService;
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

public class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    CategoriesController categoriesController;

    Usuario usuario;

    Category category;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoriesController = new CategoriesController(categoryService);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("luiz henrique dandolini dos reis");
        usuario.setUsername("luiz.reis");

        category = new Category();
        category.setDescricao("Carro");
        category.setTipo(Tipo.SAIDA);
        category.setId(1L);

        mockMvc = MockMvcBuilders.standaloneSetup(categoriesController).build();
    }

    @Test
    public void deveRetornaViewLista() throws Exception {
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setDescricao("Carro");
        categoriaDto.setTipo(Tipo.SAIDA);
        categoriaDto.setId(1L);

        when(categoryService.findAll())
                .thenReturn(Arrays.asList(categoriaDto));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/lista"))
                .andExpect(model().attributeExists("categories"));

        verify(categoryService, times(1)).findAll();

    }

    @Test
    public void testSaveNewCategory() throws Exception {
        Category categoryParam = new Category();
        categoryParam.setDescricao("Carro");
        categoryParam.setTipo(Tipo.SAIDA);


        when(categoryService.save(categoryParam)).thenReturn(this.category);

        mockMvc.perform(post("/site/categories")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(user(usuario))
                .param("tipo", this.category.getTipo().toString())
                .param("descricao", this.category.getDescricao())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/site/categories/1"));
    }

    @Test
    public void testGetNewCategoryForm() throws Exception {
        mockMvc.perform(get("/site/categories/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/form"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attributeExists("tipos"));
    }
}