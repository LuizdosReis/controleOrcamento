package br.com.springboot.controleorcamento.controllers;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("site/accounts")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping
    public String getall(Model model, Pageable pageable) {

        Page<Account> accountPage = accountService.findAll(pageable);

        PageWrapper<Account> page = new PageWrapper<>(accountPage, "site/accounts");

        List<Account> content = page.getContent();

        model.addAttribute("accounts", page.getContent());

        model.addAttribute("account", new Account());

        model.addAttribute("page", page);

        return "accounts/list";
    }

    @PostMapping
    public String salvaConta(@ModelAttribute Account conta, BindingResult errors, Model model, @AuthenticationPrincipal Usuario usuario) {

        accountService.save(conta, usuario);


        //model.addAttribute("accounts", accountService.findByUsuario(usuario, null));

        model.addAttribute("conta", new Account());

        return "accounts/lista";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        model.addAttribute("conta", accountService.findOne(id));

        return "accounts/detalhes";
    }
}
