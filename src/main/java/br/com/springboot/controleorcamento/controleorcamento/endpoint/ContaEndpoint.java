package br.com.springboot.controleorcamento.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.controleorcamento.dto.ContaDTO;
import br.com.springboot.controleorcamento.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.repository.ContaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/contas/")
public class ContaEndpoint {



    private final ContaRepository contaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ContaEndpoint(ContaRepository contaRepository, ModelMapper modelMapper) {
        this.contaRepository = contaRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "protected")
    public ResponseEntity<?> getAll(@AuthenticationPrincipal Usuario usuario, Pageable pageable){
        return new ResponseEntity<>(contaRepository.findByUsuario(usuario,pageable), HttpStatus.OK);
    }

    @PostMapping(path = "protected")
    public ResponseEntity<?> save(@Valid @RequestBody Conta conta, @AuthenticationPrincipal Usuario usuario){

        conta.setUsuario(usuario);

        conta = contaRepository.save(conta);

        return new ResponseEntity<>(modelMapper.map(conta,ContaDTO.class), HttpStatus.CREATED);
    }
}
