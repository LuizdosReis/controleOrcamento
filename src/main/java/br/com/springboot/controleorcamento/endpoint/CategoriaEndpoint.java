package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.dto.CategoriaCreateDto;
import br.com.springboot.controleorcamento.dto.CategoriaUpdateDto;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/categorias")
public class CategoriaEndpoint {

    private final CategoryService categoryService;

    public CategoriaEndpoint(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CategoriaCreateDto categoria) {
        return new ResponseEntity<>(categoryService.save(categoria), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> listaTodos(Pageable pageable,@AuthenticationPrincipal Usuario usuario){
        return new ResponseEntity<>(categoryService.findAll(pageable),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody CategoriaUpdateDto categoriaDto){
        categoryService.update(categoriaDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(categoryService.findOne(id),HttpStatus.OK);
    }


}
