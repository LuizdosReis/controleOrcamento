package br.com.springboot.controleorcamento.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.service.CategoriaService;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/categorias/")
@CrossOrigin
public class CategoriaEndpoint {

    private final CategoriaService categoriaService;

    public CategoriaEndpoint(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping(path = "protected")
    @Transactional
    public ResponseEntity<?> save(@RequestBody Categoria categoria,@AuthenticationPrincipal Usuario usuario){
        return new ResponseEntity<>(categoriaService.save(categoria,usuario), HttpStatus.CREATED);
    }

    @GetMapping(path = "protected")
    public ResponseEntity<?> listaTodos(Pageable pageable,@AuthenticationPrincipal Usuario usuario){
        return new ResponseEntity<>(categoriaService.findByUsuario(usuario,pageable),HttpStatus.OK);
    }

    @PutMapping(path = "admin/categorias")
    @Transactional
    public ResponseEntity<?> update(@Valid @RequestBody Categoria categoria){
        categoriaService.update(categoria);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
