package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.helper.CategoryHelper;
import br.com.springboot.controleorcamento.helper.PageResquestArgumentResolver;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    CategoryController controller;
    @MockBean
    private CategoryService categoryService;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(PageResquestArgumentResolver.pageRequestArgumentResolver())
                .build();
    }

    @Test
    public void deveRetornaViewLista() throws Exception {
        when(categoryService.findAll(PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(CategoryHelper.buildCategoryDto())));

        mockMvc.perform(get("/site/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/list"))
                .andExpect(model().attributeExists("categories"));

        verify(categoryService, times(1)).findAll(PageRequest.of(0, 20));

    }

    @Test
    public void testSaveNewCategory() throws Exception {
        Category categoryParam = CategoryHelper.buildCategory();
        categoryParam.setId(null);
        Category categoryReturn = CategoryHelper.buildCategory();

        when(categoryService.save(categoryParam)).thenReturn(categoryReturn);

        mockMvc.perform(post("/site/categories")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("tipo", categoryReturn.getTipo().toString())
                .param("descricao", categoryReturn.getDescricao()))
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