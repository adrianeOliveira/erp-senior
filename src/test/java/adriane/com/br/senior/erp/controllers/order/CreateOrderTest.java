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
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateOrderTest {

    @Autowired
    private OrderDtoFactory orderDtoFactory;

    @Test
    void createOrderWithSuccess() throws JsonProcessingException {
        OrderDto orderDto = orderDtoFactory.build();
        ObjectMapper mapper = new ObjectMapper();
        final OrderDto result = given()
                    .contentType(ContentType.JSON)
                    .body(mapper.writeValueAsString(orderDto))
                .when()
                    .post("/orders")
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(OrderDto.class);
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(orderDto.getStatus());
        assertThat(result.getItems().size()).isEqualTo(orderDto.getItems().size());
    }
}
