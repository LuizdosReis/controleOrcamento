package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.service.DespesaService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("v1/despesas/")
public class DespesaEndpoint {

    private final DespesaService despesaService;

    public DespesaEndpoint(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @GetMapping(path = "protected")
    public ResponseEntity<?> listaTodos(Pageable pageable, @AuthenticationPrincipal Usuario usuario) {
        return new ResponseEntity<>(despesaService.findAll(usuario,pageable), HttpStatus.OK);
    }

    @GetMapping(path = "protected/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(despesaService.getById(id), HttpStatus.OK);
    }


    @GetMapping(path = "protected/findbycategoria/{idCategoria}")
    public ResponseEntity<?> getByTipo(@PathVariable("idCategoria") long idCategoria, Pageable pageable) {
        return new ResponseEntity<>(despesaService.findByCategoria(idCategoria,pageable), HttpStatus.OK);
    }

    @GetMapping(path = "protected/findbydatas")
    public ResponseEntity<?> getByDatas(@RequestParam("dataInicial") LocalDate dataInicial,
                                        @RequestParam("dataFinal") LocalDate dataFinal, Pageable pageable) {
        return new ResponseEntity<>(despesaService.findByDataBetween(dataInicial, dataFinal, pageable), HttpStatus.OK);
    }

    @PostMapping(path = "protected")
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody Despesa despesa) {
        return new ResponseEntity<>(despesaService.save(despesa),HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "admin/gastos/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id){
        despesaService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

    @PutMapping(path = "admin/gastos")
    @Transactional
    public ResponseEntity<?> update(@Valid @RequestBody Despesa gasto) {
        despesaService.update(gasto);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
