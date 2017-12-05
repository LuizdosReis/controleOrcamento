package br.com.springboot.controleorcamento.controllers;


import br.com.springboot.controleorcamento.dto.CategoryDto;
import br.com.springboot.controleorcamento.model.Category;
import br.com.springboot.controleorcamento.model.Tipo;
import br.com.springboot.controleorcamento.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


@Controller
@RequestMapping("site/categories")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAll(Model model, Pageable pageable){

        Page<CategoryDto> categoryPage = categoryService.findAll(pageable);

        PageWrapper<CategoryDto> page = new PageWrapper<>(categoryPage,"/site/categories");

        model.addAttribute("categories", page.getContent());
        model.addAttribute("page", page);

        return "categories/list";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model){
        model.addAttribute("category", categoryService.findOne(id));

        return "categories/detalhes";
    }

    @PostMapping
    public String saveOrUpdate(@ModelAttribute Category category) {

        Category categorySaved = categoryService.save(category);

        return "redirect:/site/categories/" + categorySaved.getId();
    }

    @GetMapping("/new")
    public String newCategory(Model model){
        model.addAttribute("category", CategoryDto.builder());

        model.addAttribute("tipos", Tipo.values());

        return "categories/form";
    }

    @GetMapping("/{id}/update")
    public String updateCategory(@PathVariable long id, Model model){
        model.addAttribute("category", categoryService.findOne(id));

        model.addAttribute("tipos", Tipo.values());

        return "categories/form";
    }

    @GetMapping("/{id}/delete")
    public String deleteCategory(@PathVariable long id){

        categoryService.delete(id);

        return "redirect:/site/categories";
    }





}