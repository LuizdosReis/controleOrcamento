package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.service.ContaService;
import br.com.springboot.controleorcamento.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site/contas")
public class ContaController {

    private final ContaService contaService;
    private final UsuarioService usuarioService;

    public ContaController(ContaService contaService, UsuarioService usuarioService) {
        this.contaService = contaService;
        this.usuarioService = usuarioService;
    }


    @GetMapping
    public String getall(Model model){

        model.addAttribute("contas",contaService.findByUsuario(usuarioService.loadUserByUsername("luiz.reis"),null));

        return "contas";
    }

    @PostMapping
    public String salvaConta(@ModelAttribute Conta conta, BindingResult errors, Model model) {

        contaService.save(conta,usuarioService.loadUserByUsername("luiz.reis"));

        return "contas";
    }
}
