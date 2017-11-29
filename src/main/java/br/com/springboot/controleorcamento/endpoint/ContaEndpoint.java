package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.repository.AccountRepository;
import br.com.springboot.controleorcamento.service.AccountService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/contas/")
public class ContaEndpoint {

    private final AccountService accountService;

    private final AccountRepository accountRepository;

    public ContaEndpoint(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @GetMapping(path = "protected")
    public ResponseEntity<?> getAll(Pageable pageable) {
        return new ResponseEntity<>(accountService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping(path = "protected")
    public ResponseEntity<?> save(@Valid @RequestBody Account conta, @AuthenticationPrincipal Usuario usuario) {
        return new ResponseEntity<>(accountService.save(conta, usuario), HttpStatus.CREATED);
    }
}
