package br.com.springboot.controleorcamento.controleorcamento.controllers;

import br.com.springboot.controleorcamento.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.controleorcamento.service.ContaService;
import br.com.springboot.controleorcamento.controleorcamento.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContaController {

    private final ContaService contaService;
    private final UsuarioService usuarioService;

    public ContaController(ContaService contaService, UsuarioService usuarioService) {
        this.contaService = contaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/contas")
    public String getall(Model model){

        model.addAttribute("contas",contaService.findByUsuario(usuarioService.loadUserByUsername("luiz.reis"),null));

        return "contas";
    }

    @PostMapping(value = "/contas")
    public String salvaConta(@ModelAttribute Conta conta, BindingResult errors, Model model) {

        contaService.save(conta,usuarioService.loadUserByUsername("luiz.reis"));

        return "contas";
    }
}
