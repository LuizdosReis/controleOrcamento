package br.com.springboot.controleorcamento.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/categorias/")
public class CategoriaEndpoint {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaEndpoint(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping(path = "protected")
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody Categoria categoria){
        return new ResponseEntity<>(categoriaRepository.save(categoria), HttpStatus.CREATED);
    }

    @GetMapping(path = "protected")
    public ResponseEntity<?> listaTodos(Pageable pageable){
        return new ResponseEntity<>(categoriaRepository.findAll(pageable),HttpStatus.OK);
    }
}
