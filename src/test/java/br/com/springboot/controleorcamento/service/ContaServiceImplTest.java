package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.repository.ContaRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.config.ResourceNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ContaServiceImplTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private UsuarioService usuarioService;

    private AccountService accountService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        accountService = new AccountServiceImpl(contaRepository, usuarioService);
    }

    @Test
    public void testGetByIdNaoEncontrado() throws Exception {
        thrown.expect(ResourceNotFoundException.class);

        when(contaRepository.findById(anyLong())).thenReturn(Optional.empty());

        accountService.findOne(1L);
    }

    @Test
    public void testGetById() throws Exception {
        Account conta = new Account();
        conta.setId(1L);
        Optional<Account> contaOptional = Optional.of(conta);

        when(contaRepository.findById(1L)).thenReturn(contaOptional);

        Account contaEncontrada = accountService.findOne(1L);

        Assertions.assertThat(contaEncontrada).isEqualTo(conta);
        verify(contaRepository,times(1)).findById(1L);

    }
}
