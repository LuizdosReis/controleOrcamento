package br.com.springboot.controleorcamento.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.repository.ContaRepository;
import br.com.springboot.controleorcamento.controleorcamento.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/contas/")
@Transactional
public class ContaEndpoint {



    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ContaEndpoint(ContaRepository contaRepository, UsuarioRepository usuarioRepository) {
        this.contaRepository = contaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping(path = "protected")
    public ResponseEntity<?> getAll(@AuthenticationPrincipal Usuario usuario){
        Usuario one = usuarioRepository.findOne(usuario.getId());

        List<Conta> contas = one.getContas();

        return new ResponseEntity<>(contas, HttpStatus.OK);
    }

    @PostMapping(path = "protected")
    public ResponseEntity<?> save(@Valid @RequestBody Conta conta, @AuthenticationPrincipal Usuario usuario){

        conta = contaRepository.save(conta);

        Usuario one = usuarioRepository.findOne(usuario.getId());

        one.setConta(conta);

        usuarioRepository.save(one);

        return new ResponseEntity<>(conta, HttpStatus.CREATED);
    }
}