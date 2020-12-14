package adriane.com.br.senior.erp.service;

import adriane.com.br.senior.erp.entities.Item;
import adriane.com.br.senior.erp.entities.Order;
import adriane.com.br.senior.erp.entities.OrderStatus;
import adriane.com.br.senior.erp.exception.DiscountNotAllowedException;
import adriane.com.br.senior.erp.exception.ProductNotAllowedException;
import adriane.com.br.senior.erp.exception.ProductNotFoundException;
import adriane.com.br.senior.erp.mapper.ItemMapper;
import adriane.com.br.senior.erp.mapper.OrderMapper;
import adriane.com.br.senior.erp.repositories.ItemRepository;
import adriane.com.br.senior.erp.repositories.OrderRepository;
import adriane.com.br.senior.erp.rest.dto.ItemDto;
import adriane.com.br.senior.erp.rest.dto.OrderDto;
import adriane.com.br.senior.erp.rest.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    private final ProductService productService;

    private final OrderMapper orderMapper;

    private final ItemMapper itemMapper;

    public OrderService(OrderRepository orderRepository,
                        ItemRepository itemRepository,
                        ProductService productService,
                        OrderMapper orderMapper,
                        ItemMapper itemMapper) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.productService = productService;
        this.orderMapper = orderMapper;
        this.itemMapper = itemMapper;
    }

    @Transactional(readOnly = true)
    public List<OrderDto> listOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(orderMapper::orderEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemDto> listItemsFromOrderId(final UUID orderId) {
        List<Item> items = itemRepository.findItemByOrderId(orderId);

        return items.stream()
                .map(itemMapper::itemEntityToDto)
                .collect(Collectors.toList());

    }

    @Transactional
    public OrderDto insertOrder(OrderDto orderDto) {
        List<ItemDto> itemsDto = orderDto.getItems();
        log.info("M=insertOrder, I=validando produtos no pedido, order={}", orderDto);
        verifyItems(itemsDto, orderDto.getStatus());

        log.info("M=insertOrder, I=salvando ordem");
        Order order = orderRepository.save(orderMapper.orderDtoToEntiy(orderDto));

        itemsDto.forEach(itemDto -> {

            Item item = itemMapper.itemDtoToEntity(itemDto);
            item.setOrder(order);
            itemDto.setId(itemRepository.save(item).getId());

        });

        orderDto.setId(order.getId());
        log.info("M=insertOrder, I=Pedido salvo com sucesso, order={}", orderDto);
        return orderDto;
    }

    private void verifyItems(List<ItemDto> itemsDto, OrderStatus statusFromOrder) {
        itemsDto.forEach(itemDto -> {

            ProductDto productDto = verifyProductsFromItem(itemDto);

            if (itemDto.getDiscount() != null && itemDto.getDiscount() > 0){
                if(Boolean.TRUE.equals(productDto.getIsService())) {
                    log.error("M=verifyItems, E=Desconto não permitido para produtos do tipo serviço, product={}", productDto);
                    throw new DiscountNotAllowedException("Desconto não permitido para produtos do tipo serviço");
                }

                if (OrderStatus.ABERTA.equals(statusFromOrder)) {
                    log.error("M=verifyItems, E=Desconto não permitido para pedidos com status diferente de ABERTO");
                    throw new DiscountNotAllowedException("Desconto não permitido para pedidos com status diferente de ABERTO");
                }
            }

        });
    }

    private ProductDto verifyProductsFromItem(ItemDto itemDto) {
        ProductDto productDto = productService
                .findProductById(itemDto.getProduct().getId());

        if (productDto == null){
            log.error("M=verifyProductsFromItem, E= produto não cadastrado, item={}", itemDto);
            throw new ProductNotFoundException("Produto não existe");
        }

        if (!Boolean.TRUE.equals(productDto.getIsActive())) {
            log.error("M=verifyProductsFromItem, E= produto não pode estar desativado, product={}", productDto);
            throw new ProductNotAllowedException("Produto não pode estar desativado");
        }
        return productDto;
    }

}
