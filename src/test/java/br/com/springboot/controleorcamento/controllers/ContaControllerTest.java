package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.ContaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ContaControllerTest {

    @Mock
    ContaService contaService;


    @Mock
    Model model;

    ContaController controller;
    Usuario usuario;
    Conta conta;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ContaController(contaService);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("luiz henrique dandolini dos reis");
        usuario.setUsername("luiz.reis");

        conta = new Conta();
        conta.setId(1L);


    }

    @Test
    public void deveRetornarViewContas() throws Exception {


        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(contaService.findByUsuario(usuario,null))
                .thenReturn(new PageImpl<>(Arrays.asList(conta)));

        mockMvc.perform(get("/site/contas"))
                .andExpect(status().isOk())
                .andExpect(view().name("contas/lista"))
                .andExpect(model().attributeExists("contas"));
    }

    @Test
    public void deveRetornaViewById() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(contaService.findOne(anyLong())).thenReturn(conta);

        mockMvc.perform(get("/site/contas/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("contas/detalhes"))
                .andExpect(model().attributeExists("conta"));

    }

    @Test
    public void deveRetornaPaginaComAsContasDoUsuario() throws Exception {
        //given
        when(contaService.findByUsuario(usuario,null)).thenReturn(new PageImpl<>(Collections.singletonList(new Conta())));

        ArgumentCaptor<PageImpl<Conta>> argumentCaptor = ArgumentCaptor.forClass(PageImpl.class);


        //when
        String viewName = controller.getall(model,usuario);

        //then
        assertEquals("contas",viewName);
        verify(contaService, times(1)).findByUsuario(usuario,null);
        verify(model,times(1)).addAttribute("conta", new Conta());
        verify(model,times(1))
                .addAttribute(eq("contas"),argumentCaptor.capture());
        PageImpl<Conta> pageInController = argumentCaptor.getValue();
        assertEquals(1,pageInController.getContent().size());

    }


}