package br.com.springboot.controleorcamento.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.controleorcamento.dto.ContaGastoDTO;
import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import br.com.springboot.controleorcamento.controleorcamento.repository.CategoriaRepository;
import br.com.springboot.controleorcamento.controleorcamento.repository.ContaRepository;
import br.com.springboot.controleorcamento.controleorcamento.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("v1/gastos/")
public class GastoEndpoint {
	
	private final GastoRepository gastoRepository;
	private final CategoriaRepository categoriaRepository;
	private final ContaRepository contaRepository;

	@Autowired
	public GastoEndpoint(GastoRepository repository, CategoriaRepository categoriaRepository,
						 ContaRepository contaRepository) {
		this.gastoRepository = repository;
		this.categoriaRepository = categoriaRepository;
		this.contaRepository = contaRepository;
	}

	@GetMapping(path = "protected")
	public ResponseEntity<?> listaTodos(Pageable pageable) {
		return new ResponseEntity<>(gastoRepository.findAll(pageable),HttpStatus.OK);
	}
	
	@GetMapping(path = "protected/{id}")
	public ResponseEntity<?> getGastoById(@PathVariable("id") Long id){
		Gasto gasto = verificaSeGastoExiste(id);
		return new ResponseEntity<>(gasto,HttpStatus.OK);
	}

	
	@GetMapping(path = "protected/findbycategoria/{idCategoria}")
	public ResponseEntity<?> getByTipo(@PathVariable("idCategoria") long idCategoria,Pageable pageable){
		Categoria categoria = categoriaRepository.findOne(idCategoria);
		return new ResponseEntity<>(gastoRepository.findByCategoria(categoria,pageable),HttpStatus.OK);
	}

	@GetMapping(path = "protected/findbydatas")
	public ResponseEntity<?> getByDatas(@RequestParam("dataInicial")LocalDate dataInicial,
										@RequestParam("dataFinal") LocalDate dataFinal,Pageable pageable){
		return  new ResponseEntity<>(gastoRepository.findByDataBetween(dataInicial,dataFinal,pageable),HttpStatus.OK);
	}
	
	@PostMapping(path = "protected")
	@Transactional
	public ResponseEntity<?> save(@Valid @RequestBody ContaGastoDTO contaGastoDTO){

		Conta conta = contaRepository.findOne(contaGastoDTO.getContaId());

		Gasto gasto = contaGastoDTO.getGasto();

		gasto = gastoRepository.save(gasto);

		conta.adicionaGasto(gasto);

		contaRepository.save(conta);

		return new ResponseEntity<>(gasto,HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "admin/gastos/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id){
		verificaSeGastoExiste(id);
		gastoRepository.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PutMapping(path = "admin/gastos")
	@Transactional
	public ResponseEntity<?> update(@Valid @RequestBody Gasto gasto){
		verificaSeGastoExiste(gasto.getId());
		gastoRepository.save(gasto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	private Gasto verificaSeGastoExiste(Long id) {
		Gasto gasto = gastoRepository.findOne(id);

		if (gasto == null)
			throw new ResourceNotFoundException("Nenhum gasto encontrado no id", null);
		return gasto;
	}

}
