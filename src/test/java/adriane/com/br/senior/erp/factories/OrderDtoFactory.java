package adriane.com.br.senior.erp.factories;

import adriane.com.br.senior.erp.entities.OrderStatus;
import adriane.com.br.senior.erp.mapper.OrderMapper;
import adriane.com.br.senior.erp.repositories.OrderRepository;
import adriane.com.br.senior.erp.rest.dto.OrderDto;
import br.com.leonardoferreira.jbacon.JBacon;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoFactory extends JBacon<OrderDto> {

    private final OrderRepository orderRepository;
    private final ItemDtoFactory itemDtoFactory;
    private final OrderMapper orderMapper;
    private final Faker faker;

    public OrderDtoFactory(OrderRepository orderRepository,
                           ItemDtoFactory itemDtoFactory,
                           OrderMapper orderMapper,
                           Faker faker) {
        this.orderRepository = orderRepository;
        this.itemDtoFactory = itemDtoFactory;
        this.orderMapper = orderMapper;
        this.faker = faker;
    }

    @Override
    protected OrderDto getDefault() {
        OrderDto orderDto = OrderDto.builder()
                .status(faker.options().option(OrderStatus.values()))
                .items(itemDtoFactory.build(1, "itemDtoWithProduct"))
                .build();
        return orderDto;
    }

    @Override
    protected OrderDto getEmpty() {
        return OrderDto.builder().build();
    }

    @Override
    protected void persist(OrderDto order) {
        orderRepository.save(orderMapper.orderDtoToEntiy(order));
    }
}
