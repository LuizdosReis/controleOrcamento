package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.ContaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/site/contas")
@Slf4j
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }


    @GetMapping
    public String getall(Model model, @AuthenticationPrincipal Usuario usuario){

        model.addAttribute("contas",contaService.findByUsuario(usuario, null));

        model.addAttribute("conta",new Conta());

        return "contas/lista";
    }

    @PostMapping
    public String salvaConta(@ModelAttribute Conta conta, BindingResult errors, Model model,@AuthenticationPrincipal Usuario usuario) {

        contaService.save(conta, usuario);


        model.addAttribute("contas",contaService.findByUsuario(usuario, null));

        model.addAttribute("conta",new Conta());

        return "contas/lista";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model){
        model.addAttribute("conta",contaService.findOne(id));

        return "contas/detalhes";
    }
}
