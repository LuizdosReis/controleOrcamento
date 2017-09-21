package br.com.springboot.controleorcamento.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.controleorcamento.dao.GastoDao;
import br.com.springboot.controleorcamento.controleorcamento.dto.ContaGastoDTO;
import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.controleorcamento.repository.CategoriaRepository;
import br.com.springboot.controleorcamento.controleorcamento.repository.ContaRepository;
import br.com.springboot.controleorcamento.controleorcamento.repository.DespesaRepository;
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
@RequestMapping("v1/despesas/")
public class DespesaEndpoint {

    private final DespesaRepository despesaRepository;
    private final CategoriaRepository categoriaRepository;
    private final ContaRepository contaRepository;
    private final GastoDao gastoDao;

    @Autowired
    public DespesaEndpoint(DespesaRepository despesaRepository, CategoriaRepository categoriaRepository,
                           ContaRepository contaRepository, GastoDao gastoDao) {
        this.despesaRepository = despesaRepository;
        this.categoriaRepository = categoriaRepository;
        this.contaRepository = contaRepository;
        this.gastoDao = gastoDao;
    }

    @GetMapping(path = "protected")
    public ResponseEntity<?> listaTodos(Pageable pageable) {
        return new ResponseEntity<>(despesaRepository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "protected/{id}")
    public ResponseEntity<?> getGastoById(@PathVariable("id") Long id) {
        Despesa gasto = verificaSeGastoExiste(id);
        return new ResponseEntity<>(gasto, HttpStatus.OK);
    }


    @GetMapping(path = "protected/findbycategoria/{idCategoria}")
    public ResponseEntity<?> getByTipo(@PathVariable("idCategoria") long idCategoria, Pageable pageable) {
        Categoria categoria = categoriaRepository.findOne(idCategoria);

        return new ResponseEntity<>(gastoDao.findGastoPorCategoria(categoria.getDescricao()), HttpStatus.OK);
    }

    @GetMapping(path = "protected/findbydatas")
    public ResponseEntity<?> getByDatas(@RequestParam("dataInicial") LocalDate dataInicial,
                                        @RequestParam("dataFinal") LocalDate dataFinal, Pageable pageable) {
        return new ResponseEntity<>(despesaRepository.findByDataBetween(dataInicial, dataFinal, pageable), HttpStatus.OK);
    }

    @PostMapping(path = "protected")
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody ContaGastoDTO contaGastoDTO) {

        Conta conta = contaRepository.findOne(contaGastoDTO.getContaId());

        Despesa gasto = contaGastoDTO.getGasto();

        gasto = despesaRepository.save(gasto);

        conta.adicionaGasto(gasto);

        contaRepository.save(conta);

        return new ResponseEntity<>(gasto, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "admin/gastos/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        verificaSeGastoExiste(id);
        despesaRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping(path = "admin/gastos")
    @Transactional
    public ResponseEntity<?> update(@Valid @RequestBody Despesa gasto) {
        verificaSeGastoExiste(gasto.getId());
        despesaRepository.save(gasto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private Despesa verificaSeGastoExiste(Long id) {
        Despesa gasto = despesaRepository.findOne(id);

        if (gasto == null)
            throw new ResourceNotFoundException("Nenhum gasto encontrado no id", null);
        return gasto;
    }

}
