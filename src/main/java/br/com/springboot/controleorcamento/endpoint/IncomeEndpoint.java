package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.dto.IncomeCreateDto;
import br.com.springboot.controleorcamento.dto.IncomeReturnDto;
import br.com.springboot.controleorcamento.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/incomes")
public class IncomeEndpoint {

    private final IncomeService service;

    public IncomeEndpoint(IncomeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IncomeReturnDto save(@Valid @RequestBody IncomeCreateDto income) {
        return service.save(income);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IncomeReturnDto findById(@PathVariable("id") Long id) {
        return service.findOne(id);
    }
}
