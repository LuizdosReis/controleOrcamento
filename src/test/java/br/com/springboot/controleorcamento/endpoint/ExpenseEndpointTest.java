package br.com.springboot.controleorcamento.endpoint;


import br.com.springboot.controleorcamento.repository.ExpenseRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


public class ExpenseEndpointTest {

    @MockBean
    private ExpenseRepository expenseRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void name() throws Exception {
        
    }

    //    @Test
//    public void deveRetornaStatus401QuandoUsuarioESenhaForemIncorretosParaGetGastos() throws Exception {
//        this.mockMvc.perform(get("/v1/gastos/protected")).andExpect(status().isUnauthorized());
//    }
//
//
//    @Test
//    @WithMockUser
//    public void deveRetornaStatus200QuandoUsuarioESenhaForemCorretosParaGetGastos() throws Exception {
//
//        this.mockMvc.perform(get("/v1/gastos/protected")
//                                        .param("page","0"))
//                                                .andDo(print())
//                                                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    @WithMockUser
//    public void deveRetornaStatus200QuandoUsuarioESenhaForemCorretosParaGetGastosById() throws Exception {
//        this.mockMvc.perform(get("/v1/gastos/protected/1").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser
//    public void deveRetornaStatus404QuandoUsuarioESenhaForemCorretosEGastoNaoExistirParaGetGastosById() throws Exception {
//        mockMvc.perform(get("/v1/gastos/protected/{id}",-1L)).andExpect(status().isNotFound());
//    }


}
