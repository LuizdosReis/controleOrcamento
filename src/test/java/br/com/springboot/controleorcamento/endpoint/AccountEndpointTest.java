package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.dto.AccountDto;
import br.com.springboot.controleorcamento.helper.AccountHelper;
import br.com.springboot.controleorcamento.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpEntity<Void> header;
    private HttpEntity<Void> wrongHeader;

    @Before
    public void configHeaders() {
        String str = "{\"username\":\"luiz.reis\",\"password\":123}";
        HttpHeaders headers = restTemplate.postForEntity("/login", str, String.class).getHeaders();
        this.header = new HttpEntity<>(headers);
    }

    @Before
    public void configWrongHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "123");
        this.wrongHeader = new HttpEntity<>(headers);
    }

    @Test
    public void listAccountsWhenTokenIsCorrectShouldReturnStatus200() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts/", GET, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void listAccountsWhenTokenIsIncorrectShouldReturnStatus403() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts/", GET, wrongHeader, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void getAccountWhenTokenIsCorrectShouldReturnStatus200() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts/1", GET, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getAccountWhenTokenIsIncorrectShouldReturnStatus403() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts/1", GET, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getAccountWhenTokenIsIncorrectAndAccountDoesNotExistShouldReturnStatus404() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts/-1", GET, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteAccountWhenTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts/1", DELETE, wrongHeader, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void deleteAccountWhenTokenIsCorrectAndAccountDoesNotExistShouldReturnStatusCode404() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts/-1", DELETE, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteAccountWhenTokenIsCorrectShouldReturnStatusCode200() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts/2", DELETE, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteAccountWhenTokenIsCorrectAndAccountContainsRelationShouldReturnStatusCode500() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts/1", DELETE, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void saveAccountWhenTokenIsCorrectShouldReturnStatusCode201() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts", POST, new HttpEntity<>(AccountHelper.buildCreateAccountDto(), header.getHeaders()), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void saveAccountWhenTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts", POST,
                        new HttpEntity<>(AccountHelper.buildCreateAccountDto(), wrongHeader.getHeaders()), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void saveAccountWithIdWhenTokenIsCorrectShouldReturnStatusCode400() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts", POST,
                        new HttpEntity<>(AccountHelper.buildAccount(), header.getHeaders()), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void saveAccountInvaliddWhenTokenIsIncorrectShouldReturnStatusCode400() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts", POST,
                        new HttpEntity<>(AccountHelper.buildAccount(), header.getHeaders()), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void updateAccountTokenIsCorrectShouldReturnStatusCode200() {
        AccountDto account = AccountHelper.buildAccountDto();
        account.setDescricao("change description");
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts", PUT,
                        new HttpEntity<>(account, header.getHeaders()), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);

        ResponseEntity<Account> responseAccount = restTemplate
                .exchange("/v1/accounts/" + account.getId(), GET, header, Account.class);

        assertThat(responseAccount.getBody().getDescricao()).isEqualTo(account.getDescricao());
    }


    @Test
    public void updateAccountTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts", PUT,
                        new HttpEntity<>(AccountHelper.buildAccountDto(), wrongHeader.getHeaders()), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void updateAccountDoesNotExistTokenIsCorrectShouldReturnStatusCode404() {
        AccountDto account = AccountHelper.buildAccountDto();
        account.setId(9999L);
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts", PUT,
                        new HttpEntity<>(account, header.getHeaders()), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateAccountWithDescriptionInvalidWhenTokenIsCorrectShouldReturnStatusCode400() {
        AccountDto account = AccountHelper.buildAccountDto();
        account.setDescricao("");
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/accounts", POST,
                        new HttpEntity<>(account, header.getHeaders()), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }
}