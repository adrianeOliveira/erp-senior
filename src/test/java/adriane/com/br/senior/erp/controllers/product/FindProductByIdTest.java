package adriane.com.br.senior.erp.controllers.product;

import adriane.com.br.senior.erp.factories.ProductFactory;
import adriane.com.br.senior.erp.rest.dto.ProductDto;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FindProductByIdTest {

	@Autowired
	private ProductFactory productFactory;

	@Test
	@Disabled("metodo create não retorna o id do objeto depois de inserir na base")
	void findProductWithSuccess() {
		ProductDto product = productFactory.create();

		given()
			.contentType(ContentType.JSON)
		.when()
			.get("/products/"+product.getId())
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("id", equalTo(product.getId().toString()));
	}

	@Test
	void findProductThatNotExist() {
		UUID uuid = UUID.randomUUID();
		given()
			.contentType(ContentType.JSON)
		.when()
			.get("/products/"+uuid.toString())
		.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
	}

}
