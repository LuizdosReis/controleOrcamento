package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ResourceNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private AccountService accountService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        accountService = new AccountServiceImpl(accountRepository, usuarioService, modelMapper);
    }

    @Test
    public void testGetByIdNotFind() throws Exception {
        thrown.expect(ResourceNotFoundException.class);

        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        accountService.findOne(1L);
    }

    @Test
    public void testGetById() throws Exception {
        Account account = new Account();
        account.setId(1L);
        Optional<Account> accountOptional = Optional.of(account);

        when(accountRepository.findById(1L)).thenReturn(accountOptional);

        Account accountFind = accountService.findOne(1L);

        Assertions.assertThat(accountFind).isEqualTo(account);
        verify(accountRepository, times(1)).findById(1L);
    }
}
