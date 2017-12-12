package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.dto.IncomeCreateDto;
import br.com.springboot.controleorcamento.dto.IncomeReturnDto;
import br.com.springboot.controleorcamento.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/incomes")
public class IncomeEndpoint {

    private final IncomeService service;

    public IncomeEndpoint(IncomeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<IncomeReturnDto> save(@Valid @RequestBody IncomeCreateDto income) {
        return new ResponseEntity<>(service.save(income), HttpStatus.CREATED);
    }
}
