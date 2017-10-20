package br.com.springboot.controleorcamento.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    @RequestMapping({"","/","index"})
    public String index(){
        return "index";
    }

    @GetMapping("site/login")
    public String login() {
        return "login";
    }

    @GetMapping("site/403")
    public String error403() {
        return "error/403";
    }
}
