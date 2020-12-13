package adriane.com.br.senior.erp.mapper;

import adriane.com.br.senior.erp.entities.Item;
import adriane.com.br.senior.erp.rest.dto.ItemDto;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    private final ProductMapper productMapper;

    public ItemMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public ItemDto itemEntityToDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .product(productMapper.productEntityToDto(item.getProduct()))
                .discount(item.getDiscount())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .build();
    }

    public Item itemDtoToEntity(ItemDto dto) {
        Item item = new Item();
        item.setId(dto.getId());
        item.setDiscount(dto.getDiscount());
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getUnitPrice());
        item.setProduct(productMapper.productDtoToEntity(dto.getProduct()));
        return item;
    }
}
