package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.dto.IncomeCreateDto;
import br.com.springboot.controleorcamento.dto.IncomeDto;
import br.com.springboot.controleorcamento.helper.IncomeHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IncomeEndpointTest extends AbstractControllerRest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private String correctToken;

    @Before
    public void setUp() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();

        String str = "{\"username\":\"luiz.reis\",\"password\":123}";

        correctToken = mvc.perform(MockMvcRequestBuilders.post("/login")
                .content(str))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    @Test
    public void shouldSaveIncomeWithCorrectToken() throws Exception {

        IncomeCreateDto incomeCreateDto = IncomeHelper.buildIncomeCreateDto();

        mvc.perform(post("/v1/incomes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(incomeCreateDto))
                .header("Authorization", correctToken))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("Some description"))
                .andExpect(jsonPath("$.value").value("32.5"))
                .andExpect(jsonPath("$.received").value(Boolean.TRUE))
                .andExpect(jsonPath("$.date").value("05/12/2017"));

    }

    @Test
    public void shouldFindIncomeWithCorrectToken() throws Exception {

        mvc.perform(get("/v1/incomes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", correctToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("salary"))
                .andExpect(jsonPath("$.value").value("1500.0"))
                .andExpect(jsonPath("$.received").value(Boolean.TRUE))
                .andExpect(jsonPath("$.date").value("05/10/2017"));

    }

    @Test
    public void shouldGetErrorsFindIncomeWithParameterFormatIncorrect() throws Exception {

        mvc.perform(get("/v1/incomes/a")
                .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", correctToken))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldError.field").value("id"))
                .andExpect(jsonPath("$.fieldError.rejectedValue").value("a"))
                .andExpect(jsonPath("$.fieldError.code").value("java.lang.Long"));
    }

    @Test
    public void shouldGetIncomeNotFoundWithCorrectToken() throws Exception {

        mvc.perform(get("/v1/incomes/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", correctToken))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetErrorsWithIncorrectIncome() throws Exception {

        mvc.perform(post("/v1/incomes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(IncomeCreateDto.builder().build()))
                .header("Authorization", correctToken))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(3)))
                .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder(
                        "categoryId", "accountId","income")))
                .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
                        "CategoryId not be null", "Income not be null","AccountId not be null")));
    }


    @Test
    public void shouldGetErrorsWithIncorrectIncomeNotNull() throws Exception {

        mvc.perform(post("/v1/incomes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(IncomeCreateDto.builder()
                        .income(IncomeDto.builder().build()).build()))
                .header("Authorization", correctToken))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(5)))
                .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder(
                        "categoryId", "accountId","income.description","income.date","income.value")))
                .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
                        "CategoryId not be null","AccountId not be null","The date can not be null",
                        "The value can not be null","The description not be blank")));
    }
}
