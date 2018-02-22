package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.model.Expense;
import br.com.springboot.controleorcamento.model.User;
import br.com.springboot.controleorcamento.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("v1/expenses/")
public class ExpensesEndpoint {

    private final ExpenseService expenseService;

    public ExpensesEndpoint(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<Page<Expense>> getAll(Pageable pageable, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(expenseService.findAll(user,pageable), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Expense> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(expenseService.getById(id), HttpStatus.OK);
    }


    @GetMapping(path = "findbycategory/{idCategory}")
    public ResponseEntity<Page<Expense>> getByTipo(@PathVariable("idCategory") long idCategory, Pageable pageable) {
        return new ResponseEntity<>(expenseService.findByCategory(idCategory,pageable), HttpStatus.OK);
    }

    @GetMapping(path = "findbydates")
    public ResponseEntity<Page<Expense>> getByDates(@RequestParam("dateInitial") LocalDate dataInitial,
                                                    @RequestParam("dataEnd") LocalDate dataEnd, Pageable pageable) {
        return new ResponseEntity<>(expenseService.findByDataBetween(dataInitial, dataEnd, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Expense> save(@RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.save(expense),HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id){
        expenseService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

    @PutMapping
    public ResponseEntity<HttpStatus> update(@Valid @RequestBody Expense expense) {
        expenseService.update(expense);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
