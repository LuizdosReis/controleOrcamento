package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerTest {

    @Mock
    AccountService accountService;

    @Mock
    Model model;

    AccountController controller;
    Usuario usuario;
    Account account;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new AccountController(accountService);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("luiz henrique dandolini dos reis");
        usuario.setUsername("luiz.reis");

        account = new Account();
        account.setId(1L);
    }

    @Test
    public void shouldReturnViewAccounts() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(accountService.findAll(new PageRequest(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(account)));

        mockMvc.perform(get("/site/accounts"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/list"))
                .andExpect(model().attributeExists("accounts"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("page"));
    }

    @Test
    public void deveRetornaViewById() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(accountService.findOne(anyLong())).thenReturn(account);

        mockMvc.perform(get("/site/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/detalhes"))
                .andExpect(model().attributeExists("conta"));

    }


}