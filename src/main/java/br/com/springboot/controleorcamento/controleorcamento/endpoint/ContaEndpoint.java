package br.com.springboot.controleorcamento.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.controleorcamento.model.Conta;
import br.com.springboot.controleorcamento.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.controleorcamento.repository.ContaRepository;
import br.com.springboot.controleorcamento.controleorcamento.repository.UsuarioRepository;
import br.com.springboot.controleorcamento.controleorcamento.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/contas/")
public class ContaEndpoint {


    @Autowired
    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;


    public ContaEndpoint(ContaRepository contaRepository, UsuarioRepository usuarioRepository) {
        this.contaRepository = contaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping(path = "protected")
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody Conta conta, @AuthenticationPrincipal Usuario usuario){
        conta = contaRepository.save(conta);

        System.out.println(usuario);

      //  usuario.setConta(conta);

       // usuarioRepository.save(usuario);

        return new ResponseEntity<>(conta, HttpStatus.CREATED);
    }
}
