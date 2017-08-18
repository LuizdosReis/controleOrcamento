package br.com.springboot.controleorcamento.controleorcamento.endpoint;


import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import br.com.springboot.controleorcamento.controleorcamento.model.GastoCategorizado;
import br.com.springboot.controleorcamento.controleorcamento.repository.GastoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GastoEndpointTest {

    @MockBean
    private GastoRepository gastoRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void deveRetornaStatus401QuandoUsuarioESenhaForemIncorretosParaGetGastos() throws Exception {
        this.mockMvc.perform(get("/v1/gastos/protected")).andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser(roles = "User")
    public void deveRetornaStatus200QuandoUsuarioESenhaForemCorretosParaGetGastos() throws Exception {
        List<GastoCategorizado> gastosCategorizado = new ArrayList<>();

        gastosCategorizado.add(new GastoCategorizado(new Categoria("Carro"), new BigDecimal("32.50")));

        List<Gasto> gastos = asList(new Gasto(1L, "Gasolina", LocalDate.now(), gastosCategorizado),
                new Gasto(1L, "Filtro", LocalDate.now(), gastosCategorizado));

        when(gastoRepository.findAll()).thenReturn(gastos);

        this.mockMvc.perform(get("/v1/gastos/protected")
                                        .param("page","0"))
                                                .andDo(print())
                                                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = "User")
    public void deveRetornaStatus200QuandoUsuarioESenhaForemCorretosParaGetGastosById() throws Exception {
        List<GastoCategorizado> gastosCategorizado = new ArrayList<>();

        gastosCategorizado.add(new GastoCategorizado(new Categoria("Carro"), new BigDecimal("32.50")));

        Gasto gasto = new Gasto(1L, "Gasolina", LocalDate.now(), gastosCategorizado);

        given(gastoRepository.findOne(gasto.getId())).willReturn(gasto);

        this.mockMvc.perform(get("/v1/gastos/protected/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "User")
    public void deveRetornaStatus404QuandoUsuarioESenhaForemCorretosEGastoNaoExistirParaGetGastosById() throws Exception {
        mockMvc.perform(get("/v1/gastos/protected/{id}",-1L)).andExpect(status().isNotFound());
    }


}
