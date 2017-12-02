package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.helper.ContaHelper;
import br.com.springboot.controleorcamento.helper.PageResquestArgumentResolver;
import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountControllerTest {

    @MockBean
    AccountService accountService;

    @Autowired
    AccountController controller;

    private Account account;

    private MockMvc mockMvc;



    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(PageResquestArgumentResolver.pageRequestArgumentResolver())
         .build();

        account = ContaHelper.criaConta();
    }

    @Test
    public void shouldReturnViewAccounts() throws Exception {
        when(accountService.findAll(PageRequest.of(0,20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(account)));

        mockMvc.perform(get("/site/accounts").param("page","0"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/list"))
                .andExpect(model().attributeExists("accounts"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("page"));
    }

    @Test
    public void deveRetornaViewById() throws Exception {
        when(accountService.findOne(anyLong())).thenReturn(account);

        mockMvc.perform(get("/site/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/detalhes"))
                .andExpect(model().attributeExists("conta"));

    }


}