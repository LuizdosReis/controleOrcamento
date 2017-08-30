package br.com.springboot.controleorcamento.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.repository.UsuarioRepository;
import br.com.springboot.controleorcamento.controleorcamento.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/user/")
public class UsuarioEndpoint {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioEndpoint(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Usuario usuario){
        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.OK);
    }

    @GetMapping("/{name.sobrenome}")
    public ResponseEntity<?> getByName(@PathVariable("name.sobrenome") String name){
        return new ResponseEntity<>(usuarioRepository.findByUsername(name),HttpStatus.OK);
    }

}
