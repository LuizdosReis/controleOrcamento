package br.com.springboot.controleorcamento.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.service.ContaService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/contas/")
public class ContaEndpoint {

    private final ContaService contaService;

    public ContaEndpoint(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping(path = "protected")
    public ResponseEntity<?> getAll(@AuthenticationPrincipal Usuario usuario, Pageable pageable){
        return new ResponseEntity<>(contaService.findByUsuario(usuario,pageable), HttpStatus.OK);
    }

    @PostMapping(path = "protected")
    public ResponseEntity<?> save(@Valid @RequestBody Conta conta, @AuthenticationPrincipal Usuario usuario){
        return new ResponseEntity<>(contaService.save(conta,usuario), HttpStatus.CREATED);
    }
}
