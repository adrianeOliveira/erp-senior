package adriane.com.br.senior.erp.controllers.product;

import adriane.com.br.senior.erp.entities.Product;
import adriane.com.br.senior.erp.factories.ProductFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateProductTest {

    @Autowired
    private ProductFactory productFactory;

    @Test
    void createProductWithSuccess() throws JsonProcessingException {
        Product product = productFactory.build();
        ObjectMapper objectMapper = new ObjectMapper();

        Product result = given()
                .contentType("application/json")
                .body(objectMapper.writeValueAsString(product))
            .when()
                .post("/products")
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                    .as(Product.class);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getPrice()).isEqualTo(product.getPrice());
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getIsActive()).isEqualTo(product.getIsActive());
        assertThat(result.getIsService()).isEqualTo(product.getIsService());

    }
}
