package br.com.springboot.controleorcamento.endpoint;


import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.repository.DespesaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DespesaEndpointTest {

    @MockBean
    private DespesaRepository despesaRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void deveRetornaStatus401QuandoUsuarioESenhaForemIncorretosParaGetGastos() throws Exception {
        this.mockMvc.perform(get("/v1/gastos/protected")).andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser
    public void deveRetornaStatus200QuandoUsuarioESenhaForemCorretosParaGetGastos() throws Exception {

        this.mockMvc.perform(get("/v1/gastos/protected")
                                        .param("page","0"))
                                                .andDo(print())
                                                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    public void deveRetornaStatus200QuandoUsuarioESenhaForemCorretosParaGetGastosById() throws Exception {
        this.mockMvc.perform(get("/v1/gastos/protected/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void deveRetornaStatus404QuandoUsuarioESenhaForemCorretosEGastoNaoExistirParaGetGastosById() throws Exception {
        mockMvc.perform(get("/v1/gastos/protected/{id}",-1L)).andExpect(status().isNotFound());
    }


}
