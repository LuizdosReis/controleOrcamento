package br.com.springboot.controleorcamento.service;

import br.com.springboot.controleorcamento.model.Conta;
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

    private ContaService contaService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        contaService = new ContaServiceImpl(contaRepository);
    }

    @Test
    public void testGetByIdNaoEncontrado() throws Exception {
        thrown.expect(ResourceNotFoundException.class);

        when(contaRepository.findById(anyLong())).thenReturn(Optional.empty());

        contaService.findOne(1L);
    }

    @Test
    public void testGetById() throws Exception {
        Conta conta = new Conta();
        conta.setId(1L);
        Optional<Conta> contaOptional = Optional.of(conta);

        when(contaRepository.findById(1L)).thenReturn(contaOptional);

        Conta contaEncontrada = contaService.findOne(1L);

        Assertions.assertThat(contaEncontrada).isEqualTo(conta);
        verify(contaRepository,times(1)).findById(1L);

    }
}
