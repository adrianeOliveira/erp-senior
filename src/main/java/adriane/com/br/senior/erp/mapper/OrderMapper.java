package adriane.com.br.senior.erp.mapper;

import adriane.com.br.senior.erp.entities.Order;
import adriane.com.br.senior.erp.rest.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDto OrderEntityToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .status(order.getStatus())
                .build();
    }

    public Order OrderDtoToEntiy(OrderDto dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setStatus(dto.getStatus());
        return order;
    }
}
