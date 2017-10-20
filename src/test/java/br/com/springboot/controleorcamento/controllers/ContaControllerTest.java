package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.service.ContaService;
import br.com.springboot.controleorcamento.service.UsuarioService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import static org.junit.Assert.assertEquals;

public class ContaControllerTest {

    @Mock
    ContaService contaService;

    @Mock
    UsuarioService usuarioService;

    @Mock
    Model model;

    ContaController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ContaController(contaService,usuarioService);
    }

    @Test
    public void deveRetornaPaginaComAsContasDoUsuario() throws Exception {

       // String viewName = controller.getall(model);

       // assertEquals("contas",viewName);

    }


}