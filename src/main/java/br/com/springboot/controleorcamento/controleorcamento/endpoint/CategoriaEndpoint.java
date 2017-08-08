package br.com.springboot.controleorcamento.controleorcamento.endpoint;


import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import br.com.springboot.controleorcamento.controleorcamento.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1")
public class CategoriaEndpoint {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaEndpoint(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping(path = "protected/categoria")
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody Categoria categoria){
        return new ResponseEntity<>(categoriaRepository.save(categoria), HttpStatus.CREATED);
    }
}
