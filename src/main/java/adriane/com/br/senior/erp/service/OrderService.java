package adriane.com.br.senior.erp.service;

import adriane.com.br.senior.erp.entities.Item;
import adriane.com.br.senior.erp.entities.Order;
import adriane.com.br.senior.erp.entities.OrderStatus;
import adriane.com.br.senior.erp.exception.DiscountNotAllowedException;
import adriane.com.br.senior.erp.exception.OrderNotFoundException;
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

    public OrderService(final OrderRepository orderRepository,
                        final ItemRepository itemRepository,
                        final ProductService productService,
                        final OrderMapper orderMapper,
                        final ItemMapper itemMapper) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.productService = productService;
        this.orderMapper = orderMapper;
        this.itemMapper = itemMapper;
    }

    @Transactional(readOnly = true)
    public List<OrderDto> listOrders() {
        final List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> {
                    final OrderDto orderDto = orderMapper.orderEntityToDto(order);
                    orderDto.setItems(listItemsFromOrderId(order.getId()));
                    return orderDto;
                }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemDto> listItemsFromOrderId(final UUID orderId) {
        final List<Item> items = itemRepository.findItemByOrderId(orderId);

        return items.stream()
                .map(itemMapper::itemEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateOrder(final UUID orderId, final OrderDto orderDto) {
        log.info("M=updateOrder, I=atualizando pedido, order={}", orderDto);
        if (orderRepository.existsById(orderId)) {
            orderDto.setId(orderId);
            final Order order = saveOrder(orderMapper.orderDtoToEntiy(orderDto));
            saveItems(itemMapper.itemListDtoToEntityList(orderDto.getItems()), order);
        } else {
            log.error("M=updateOrder, E=Pedido não existe para ser atualizado, order={}", orderDto);
            throw new OrderNotFoundException("Pedido não existe para ser atualizado.");
        }
    }

    @Transactional
    public OrderDto insertOrder(final OrderDto orderDto) {
        final List<ItemDto> itemsDto = orderDto.getItems();
        log.info("M=insertOrder, I=validando produtos no pedido, order={}", orderDto);
        verifyItems(itemsDto, orderDto.getStatus());

        final Order order = saveOrder(orderMapper.orderDtoToEntiy(orderDto));

        final List<Item> items = saveItems(itemMapper.itemListDtoToEntityList(itemsDto), order);

        orderDto.setId(order.getId());
        orderDto.setItems(itemMapper.itemEntityListToListDto(items));
        log.info("M=insertOrder, I=Pedido salvo com sucesso, order={}", orderDto);
        return orderDto;
    }

    private Order saveOrder(final Order order) {
        log.info("M=saveOrder, I=salvando ordem");
        return orderRepository.save(order);
    }

    private List<Item> saveItems(final List<Item> items, final Order order) {
        log.info("M=saveItems, I=Salvando items da ordem, orderid={}", order.getId());
        items.forEach(item -> item.setOrder(order));
        return itemRepository.saveAll(items);
    }

    private void verifyItems(final List<ItemDto> itemsDto,
                             final OrderStatus statusFromOrder) {
        itemsDto.forEach(itemDto -> {

            final ProductDto productDto = verifyProductsFromItem(itemDto);

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

    private ProductDto verifyProductsFromItem(final ItemDto itemDto) {
        final ProductDto productDto = productService
                .findProductById(itemDto.getProduct().getId());

        if (productDto == null){
            log.error("M=verifyProductsFromItem, E=Produto não cadastrado, item={}", itemDto);
            throw new ProductNotFoundException("Produto não existe");
        }

        if (!Boolean.TRUE.equals(productDto.getIsActive())) {
            log.error("M=verifyProductsFromItem, E=produto não pode estar desativado, product={}", productDto);
            throw new ProductNotAllowedException("Produto não pode estar desativado");
        }
        return productDto;
    }

}
