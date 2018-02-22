package br.com.springboot.controleorcamento.endpoint;

import br.com.springboot.controleorcamento.dto.CategoryCreateDto;
import br.com.springboot.controleorcamento.dto.CategoryDto;
import br.com.springboot.controleorcamento.helper.CategoryHelper;
import br.com.springboot.controleorcamento.model.Category;
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
public class CategoryEndpointTest {

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
    public void listCategoriesWhenTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories/", GET, wrongHeader, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);

    }

    @Test
    public void listCategoriesWhenTokenCorrectShouldReturnStatusCode200() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories/", GET, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("totalElements", "3");
        assertThat(response.getBody()).contains("id", "1");
    }

    @Test
    public void getCategoryWhenTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories/1", GET, wrongHeader, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);

    }

    @Test
    public void getCategoryWhenTokenIsCorrectShouldReturnStatusCode200() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories/1", GET, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }

    @Test
    public void getCategoryWhenTokenIsCorrectAndCategoryDoesNotExistShouldReturnStatusCode404() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories/-1", GET, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);

    }

    @Test
    public void deleteCategoryWhenTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories/1", DELETE, wrongHeader, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void deleteCategoryWhenTokenIsCorrectAndCategoryDoesNotExistShouldReturnStatusCode404() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories/-1", DELETE, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteCategoryWhenTokenIsCorrectShouldReturnStatusCode200() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories/3", DELETE, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteCategoryWhenTokenIsCorrectAndCategoryContainsRelationShouldReturnStatusCode500() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories/1", DELETE, header, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void saveCategoryWhenTokenIsCorrectShouldReturnStatusCode201() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories", POST, new HttpEntity<>(CategoryHelper.builderCreateCategoryDto(), header.getHeaders()), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void saveCategoryWhenTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories", POST,
                        new HttpEntity<>(CategoryHelper.builderCreateCategoryDto(), wrongHeader.getHeaders()), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void saveCategoryWithIdWhenTokenIsCorrectShouldReturnStatusCode400() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories", POST,
                        new HttpEntity<>(CategoryHelper.buildCategory(), header.getHeaders()), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void saveCategoryInvalidWhenTokenIsCorrectShouldReturnStatusCode400() {
        CategoryCreateDto category = CategoryHelper.builderCreateCategoryDto();
        category.setDescription("");
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories", POST,
                        new HttpEntity<>(category, header.getHeaders()), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void updateCategoryTokenIsCorrectShouldReturnStatusCode200() {
        CategoryDto category = CategoryHelper.buildCategoryDto();
        category.setDescription("change description");
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories", PUT,
                        new HttpEntity<>(category, header.getHeaders()), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        ResponseEntity<Category> responseCategory = restTemplate
                .exchange("/v1/categories/" + category.getId(), GET, header, Category.class);

        assertThat(responseCategory.getBody().getDescription()).isEqualTo(category.getDescription());
    }

    @Test
    public void updateCategoryTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories", PUT,
                        new HttpEntity<>(CategoryHelper.buildCategoryDto(), wrongHeader.getHeaders()), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void updateCategoryDoesNotExistTokenIsCorrectShouldReturnStatusCode404() {
        CategoryDto category = CategoryHelper.buildCategoryDto();
        category.setId(9999L);
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories", PUT,
                        new HttpEntity<>(category, header.getHeaders()), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateCategoryWithDescriptionInvalidWhenTokenIsCorrectShouldReturnStatusCode400() {
        Category category = CategoryHelper.buildCategory();
        category.setDescription("");
        ResponseEntity<String> response = restTemplate
                .exchange("/v1/categories", POST,
                        new HttpEntity<>(category, header.getHeaders()), String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }


}