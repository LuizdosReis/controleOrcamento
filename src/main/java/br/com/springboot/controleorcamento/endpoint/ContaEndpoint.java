package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.repository.ContaRepository;
import br.com.springboot.controleorcamento.service.AccountService;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Joins;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/contas/")
public class ContaEndpoint {

    private final AccountService accountService;

    private final ContaRepository contaRepository;

    public ContaEndpoint(AccountService accountService, ContaRepository contaRepository) {
        this.accountService = accountService;
        this.contaRepository = contaRepository;
    }

    @GetMapping(path = "protected")
    public ResponseEntity<?> getAll(Pageable pageable){
        return new ResponseEntity<>(accountService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping(path = "protected")
    public ResponseEntity<?> save(@Valid @RequestBody Account conta, @AuthenticationPrincipal Usuario usuario){
        return new ResponseEntity<>(accountService.save(conta,usuario), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> find(
            @Joins({
                    @Join(path = "despesas", alias = "despesas")
            })
            @And({
                    @Spec(path="despesas.descricao",spec = Like.class),
                    @Spec(path="descricao",spec = Like.class)
            }) Specification<Account> spec){
        return new ResponseEntity<>(contaRepository.findAll(spec), HttpStatus.CREATED);
    }
}
