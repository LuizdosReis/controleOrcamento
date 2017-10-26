package br.com.springboot.controleorcamento.controllers;


import br.com.springboot.controleorcamento.converter.CategoriaConverter;
import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/site/categorias")
@Slf4j
public class CategoriaController {

    private final CategoriaService categoriaService;

    private final CategoriaConverter converter;

    public CategoriaController(CategoriaService categoriaService, CategoriaConverter converter) {
        this.categoriaService = categoriaService;
        this.converter = converter;
    }

    @GetMapping
    public String getall(Model model, @AuthenticationPrincipal Usuario usuario){


        List<Categoria> categorias = categoriaService.findByUsuario(usuario, null).getContent();

        model.addAttribute("categorias",categorias.stream()
                .map(categoria -> converter.convertToDto(categoria))
                .collect(Collectors.toList()));

        return "categorias/lista";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model){
        model.addAttribute("categoria",categoriaService.findOne(id));

        return "categorias/detalhes";
    }





}
