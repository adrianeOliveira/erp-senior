package adriane.com.br.senior.erp.rest.controllers;

import adriane.com.br.senior.erp.rest.dto.ItemDto;
import adriane.com.br.senior.erp.rest.dto.OrderDto;
import adriane.com.br.senior.erp.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> listOrders() {
        log.info("M=listOrders, I= Buscando todas as ordens");
       return orderService.listOrders();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto insertOrder(@RequestBody final OrderDto orderDto) {
        return orderService.insertOrder(orderDto);
    }

    @GetMapping(value = "/{orderId}/items")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> listItemsFromOrderId(@PathVariable final UUID orderId) {
        log.info("M=listItemsFromOrderId, orderId={}", orderId);
        return orderService.listItemsFromOrderId(orderId);
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrderWithItems(@PathVariable final UUID orderId,
                                     @RequestBody final OrderDto orderDto) {
        orderService.updateOrder(orderId, orderDto);
    }


}
