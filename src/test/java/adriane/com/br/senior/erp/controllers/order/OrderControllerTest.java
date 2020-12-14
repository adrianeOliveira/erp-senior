package adriane.com.br.senior.erp.controllers.order;

import adriane.com.br.senior.erp.factories.OrderDtoFactory;
import adriane.com.br.senior.erp.rest.dto.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @Autowired
    private OrderDtoFactory orderDtoFactory;

    @Test
    void createOrderWithSuccess() throws JsonProcessingException {
        OrderDto orderDto = orderDtoFactory.build();
        ObjectMapper mapper = new ObjectMapper();
        given()
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(orderDto))
                .when()
                .post("/orders")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue());
    }
}
