package br.com.springboot.controleorcamento.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.repository.CategoriaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoriaEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaRepository categoriaRepository;


    @Test
    public void save() throws Exception {
    }

    @Test
    @WithMockUser
    public void listaTodos() throws Exception {
        given(categoriaRepository.findAll(new PageRequest(0,20)))
                .willReturn(new PageImpl<Categoria>(Collections.singletonList(new Categoria("Carro"))));


        mockMvc.perform(get("/v1/categorias/protected").param("page","0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\":[{\"id\":null,\"descricao\":\"Carro\"}],\"last\":true,\"totalElements\":1,\"totalPages\":1,\"size\":0,\"number\":0,\"sort\":null,\"first\":true,\"numberOfElements\":1}"));

    }

    public void getByid(){
        given(categoriaRepository.findById(1L))
                .willReturn(new Categoria("Carro"));
    }

    @Test
    public void update() throws Exception {
    }

}