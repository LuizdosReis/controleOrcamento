package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.repository.CategoriaRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoriaEndpointTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;

    @Before
    public void setUp(){
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setDescricao("Carro");

        given(categoriaRepository.save(categoria)).willReturn(categoria);
    }

//    @Test
//    @WithMockUser
//    public void deveRetornaStatusCriadoECategoriaCriada() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//
//        mockMvc.perform(post("/v1/categorias/protected")
//                .contentType(APPLICATION_JSON_UTF8)
//                .content(mapper.writeValueAsBytes(categoria)))
//
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id",is(1)))
//                .andExpect(jsonPath("$.descricao",is("Carro")));
//    }
//
//    @Test
//    @WithMockUser
//    public void deveLancarExecaoDeValidacaoDaDescricao() throws Exception {
//        categoria.setDescricao("");
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//
//        mockMvc.perform(post("/v1/categorias/protected")
//                .contentType(APPLICATION_JSON_UTF8)
//                .content(mapper.writeValueAsBytes(categoria)))
//
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.fieldMessage",is("A descrição não pode estar em branco")));
//    }
//
//    @Test
//    public void NaoDevePermitirCriacaoDeCategoriaDeUsuarioNaoAutenticado() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//        mockMvc.perform(post("/v1/categorias/protected")
//                .contentType(APPLICATION_JSON_UTF8)
//                .content(mapper.writeValueAsBytes(categoria)))
//
//                .andDo(print())
//                .andExpect(status().isUnauthorized());
//    }
//
//
//    @Test
//    @WithMockUser
//    public void listaTodos() throws Exception {
//        given(categoriaRepository.findAll(new PageRequest(0,20)))
//                .willReturn(new PageImpl<Categoria>(Collections.singletonList(categoria)));
//
//        mockMvc.perform(get("/v1/categorias/protected").param("page","0"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].descricao", is("Carro")))
//                .andExpect(jsonPath("$.content[0].id",is(1)));
//    }
//
//    @Test
//    @WithMockUser
//    public void listaTodosComPaginaInexistente() throws Exception {
//        given(categoriaRepository.findAll(new PageRequest(6,20)))
//                .willReturn(new PageImpl<Categoria>(Collections.singletonList(null)));
//
//        mockMvc.perform(get("/v1/categorias/protected").param("page","6"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

    @Test
    public void update() throws Exception {
    }

}