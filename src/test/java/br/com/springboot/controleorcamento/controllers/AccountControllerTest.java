package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AccountControllerTest {

    @Mock
    AccountService accountService;


    @Mock
    Model model;

    AccountController controller;
    Usuario usuario;
    Account conta;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new AccountController(accountService);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("luiz henrique dandolini dos reis");
        usuario.setUsername("luiz.reis");

        conta = new Account();
        conta.setId(1L);
    }

    @Test
    public void deveRetornarViewContas() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(accountService.findAll(null))
                .thenReturn(new PageImpl<>(Arrays.asList(conta)));

        mockMvc.perform(get("/site/accounts"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/lista"))
                .andExpect(model().attributeExists("accounts"));
    }

    @Test
    public void deveRetornaViewById() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(accountService.findOne(anyLong())).thenReturn(conta);

        mockMvc.perform(get("/site/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/detalhes"))
                .andExpect(model().attributeExists("conta"));

    }


}