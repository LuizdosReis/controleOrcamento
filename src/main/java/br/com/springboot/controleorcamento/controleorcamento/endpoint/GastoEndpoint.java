package br.com.springboot.controleorcamento.controleorcamento.endpoint;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import br.com.springboot.controleorcamento.controleorcamento.repository.GastoRepository;

@RestController
@RequestMapping("v1")
public class GastoEndpoint {
	
	private final GastoRepository repository;
	
	@Autowired
	public GastoEndpoint(GastoRepository repository) {
		this.repository = repository;
	}

	@GetMapping(path = "protected/gastos")
	public ResponseEntity<?> listaTodos(Pageable pageable) {
		return new ResponseEntity<>(repository.findAll(pageable),HttpStatus.OK);
	}
	
	@GetMapping(path = "protected/gastos/{id}")
	public ResponseEntity<?> getGastoById(@PathVariable("id") Long id){
		Gasto gasto = verificaSeGastoExiste(id);
		return new ResponseEntity<>(gasto,HttpStatus.OK);
	}

	
	@GetMapping(path = "protected/gastos/findbytipo/{tipo}")
	public ResponseEntity<?> getByTipo(@PathVariable("tipo") String tipo){
		return new ResponseEntity<>(repository.findByTipo(tipo),HttpStatus.OK);
	}
	
	@PostMapping(path = "admin/gastos")
	@Transactional
	public ResponseEntity<?> save(@Valid @RequestBody Gasto gasto){
		return new ResponseEntity<>(repository.save(gasto),HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "admin/gastos/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id){
		verificaSeGastoExiste(id);
		repository.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PutMapping(path = "admin/gastos")
	@Transactional
	public ResponseEntity<?> update(@Valid @RequestBody Gasto gasto){
		verificaSeGastoExiste(gasto.getId());
		repository.save(gasto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	private Gasto verificaSeGastoExiste(Long id) {
		Gasto gasto = repository.findOne(id);

		if (gasto == null)
			throw new ResourceNotFoundException("Nenhum gasto encontrado no id", null);
		return gasto;
	}

}
