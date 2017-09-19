package br.com.springboot.controleorcamento.controleorcamento.controllers;

import br.com.springboot.controleorcamento.controleorcamento.repository.ContaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContaController {

    private final ContaRepository contaRepository;

    public ContaController(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @RequestMapping("/contas")
    public String getall(Model model){

        model.addAttribute("contas",contaRepository.findAll());

        return "contas";
    }
}
