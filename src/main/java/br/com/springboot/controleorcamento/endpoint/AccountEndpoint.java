package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.dto.AccountCreateDto;
import br.com.springboot.controleorcamento.dto.AccountDto;
import br.com.springboot.controleorcamento.dto.AccountUpdateDto;
import br.com.springboot.controleorcamento.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/accounts")
public class AccountEndpoint {

    private final AccountService accountService;


    public AccountEndpoint(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> save(@RequestBody AccountCreateDto accountCreateDto) {
        return new ResponseEntity<>(accountService.save(accountCreateDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> update(@RequestBody AccountUpdateDto accountCreateDto) {
        accountService.update(accountCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AccountDto>> getAll(Pageable pageable) {
        return new ResponseEntity<>(accountService.findAll(pageable), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(accountService.findOneDto(id), HttpStatus.OK);
    }
}
