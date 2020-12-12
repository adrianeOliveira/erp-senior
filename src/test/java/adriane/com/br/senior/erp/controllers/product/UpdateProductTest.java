package adriane.com.br.senior.erp.controllers.product;

import adriane.com.br.senior.erp.entities.Product;
import adriane.com.br.senior.erp.factories.ProductFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateProductTest {

    @Autowired
    private ProductFactory productFactory;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Faker faker;

    @Test
    void updateProductWithSuccess() throws JsonProcessingException {
        Product product = productFactory.create();
        String newName = faker.funnyName().name();
        product.setName(newName);

        given()
            .contentType("application/json")
            .body(mapper.writeValueAsString(product))
        .when()
            .put("/products/"+product.getId())
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void updateProductThatNotExist() throws JsonProcessingException {
        Product product = productFactory.create();
        given()
            .contentType("application/json")
            .body(mapper.writeValueAsString(product))
        .when()
            .put("/products/" + UUID.randomUUID())
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
