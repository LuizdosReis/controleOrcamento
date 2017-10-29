package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.dto.CategoriaCreateDto;
import br.com.springboot.controleorcamento.dto.CategoriaUpdateDto;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.CategoriaService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/categorias")
public class CategoriaEndpoint {

    private final CategoriaService categoriaService;

    public CategoriaEndpoint(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CategoriaCreateDto categoria, @AuthenticationPrincipal Usuario usuario) {
        return new ResponseEntity<>(categoriaService.save(categoria,usuario), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> listaTodos(Pageable pageable,@AuthenticationPrincipal Usuario usuario){
        return new ResponseEntity<>(categoriaService.findByUsuario(usuario,pageable),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody CategoriaUpdateDto categoriaDto, @AuthenticationPrincipal Usuario usuario){
        categoriaService.update(categoriaDto,usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        categoriaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id,@AuthenticationPrincipal Usuario usuario){
        return new ResponseEntity<>(categoriaService.findById(id,usuario),HttpStatus.OK);
    }


}
