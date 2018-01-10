package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.dto.CategoriaUpdateDto;
import br.com.springboot.controleorcamento.dto.CategoryCreateDto;
import br.com.springboot.controleorcamento.dto.CategoryDto;
import br.com.springboot.controleorcamento.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/categories")
public class CategoriaEndpoint {

    private final CategoryService categoryService;

    public CategoriaEndpoint(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> save(@Valid @RequestBody CategoryCreateDto categoria) {
        return new ResponseEntity<>(categoryService.save(categoria), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDto>> getAll(Pageable pageable){
        return new ResponseEntity<>(categoryService.findAll(pageable),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> update(@Valid @RequestBody CategoriaUpdateDto categoriaDto){
        categoryService.update(categoriaDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id){
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(categoryService.findOne(id),HttpStatus.OK);
    }


}
