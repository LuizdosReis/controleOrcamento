package br.com.springboot.controleorcamento.controllers;


import br.com.springboot.controleorcamento.dto.CategoriaCreateDto;
import br.com.springboot.controleorcamento.dto.CategoriaDto;
import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Tipo;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/site/categorias")
@Slf4j
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String getAll(Model model, @AuthenticationPrincipal Usuario usuario){

        model.addAttribute("categorias", categoriaService.findByUsuario(usuario));

        return "categorias/lista";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model){
        model.addAttribute("categoria",categoriaService.findOne(id));

        return "categorias/detalhes";
    }

    @PostMapping
    public String saveOrUpdate(@ModelAttribute Categoria categoria, @AuthenticationPrincipal Usuario usuario) {

        Categoria categoriaSalva = categoriaService.save(categoria, usuario);

        return "redirect:/site/categorias/" + categoriaSalva.getId();
    }

    @GetMapping("/new")
    public String newCategory(Model model){
        model.addAttribute("categoria", new CategoriaDto());

        model.addAttribute("tipos", Tipo.values());

        return "categorias/form";
    }

    @GetMapping("/{id}/update")
    public String updateCategory(@PathVariable long id, Model model,@AuthenticationPrincipal Usuario usuario){
        model.addAttribute("categoria", categoriaService.findById(id,usuario));

        model.addAttribute("tipos", Tipo.values());

        return "categorias/form";
    }

    @GetMapping("/{id}/delete")
    public String deleteCategory(@PathVariable long id,@AuthenticationPrincipal Usuario usuario){

        categoriaService.delete(id,usuario);

       // model.addAttribute("tipos", Tipo.values());

        return "categorias/form";
    }





}
