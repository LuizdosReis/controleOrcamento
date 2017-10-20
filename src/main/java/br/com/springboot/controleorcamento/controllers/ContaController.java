package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.ContaService;
import br.com.springboot.controleorcamento.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/site/contas")
@Slf4j
public class ContaController {

    private final ContaService contaService;
    private final UsuarioService usuarioService;

    public ContaController(ContaService contaService, UsuarioService usuarioService) {
        this.contaService = contaService;
        this.usuarioService = usuarioService;
    }


    @GetMapping
    public String getall(Model model, Principal principal){
        Authentication authentication = (Authentication) principal;
        Usuario usuario = (Usuario) authentication.getPrincipal();

        model.addAttribute("contas",contaService.findByUsuario(usuario,null));

        return "contas";
    }

    @PostMapping
    public String salvaConta(@ModelAttribute Conta conta, BindingResult errors, Model model) {

        contaService.save(conta,usuarioService.loadUserByUsername("luiz.reis"));

        return "contas";
    }
}
