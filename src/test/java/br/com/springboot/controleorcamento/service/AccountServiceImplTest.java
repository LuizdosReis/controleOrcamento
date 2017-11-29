package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Account;
import br.com.springboot.controleorcamento.model.Usuario;
import br.com.springboot.controleorcamento.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        accountService = new AccountServiceImpl(accountRepository, usuarioService);
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
        verify(accountRepository,times(1)).findById(1L);
    }

    @Test
    public void testFindAll() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("luiz henrique dandolini dos reis");
        usuario.setUsername("luiz.reis");

        Account account = new Account();
        account.setId(1L);

        Pageable pageable = Mockito.mock(Pageable.class);

        when(usuarioService.getCurrentUser()).thenReturn(usuario);

        when(accountRepository.findByUsuario(usuario,pageable)).thenReturn(new PageImpl<>(Collections.singletonList(account)));

        Page<Account> page = accountService.findAll(pageable);

        System.out.println(page.getContent());

        Assertions.assertThat(page.getContent().get(0)).isEqualTo(account);
        verify(accountRepository,times(1)).findByUsuario(usuario,pageable);
    }
}
