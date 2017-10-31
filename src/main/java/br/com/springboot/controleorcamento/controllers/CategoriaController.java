package br.com.springboot.controleorcamento.controllers;


import br.com.springboot.controleorcamento.dto.CategoriaDto;
import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.model.Tipo;
import br.com.springboot.controleorcamento.service.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/site/categorias")
@Slf4j
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String getAll(Model model){

        model.addAttribute("categorias", categoriaService.findAll());

        return "categorias/lista";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model){
        model.addAttribute("categoria",categoriaService.findOne(id));

        return "categorias/detalhes";
    }

    @PostMapping
    public String saveOrUpdate(@ModelAttribute Categoria categoria) {

        Categoria categorySaved = categoriaService.save(categoria);

        return "redirect:/site/categorias/" + categorySaved.getId();
    }

    @GetMapping("/new")
    public String newCategory(Model model){
        model.addAttribute("categoria", new CategoriaDto());

        model.addAttribute("tipos", Tipo.values());

        return "categorias/form";
    }

    @GetMapping("/{id}/update")
    public String updateCategory(@PathVariable long id, Model model){
        model.addAttribute("categoria", categoriaService.findOne(id));

        model.addAttribute("tipos", Tipo.values());

        return "categorias/form";
    }

    @GetMapping("/{id}/delete")
    public String deleteCategory(@PathVariable long id){

        categoriaService.delete(id);

        return "redirect:/site/categorias";
    }





}
