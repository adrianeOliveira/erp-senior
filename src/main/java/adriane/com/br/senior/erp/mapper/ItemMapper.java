package adriane.com.br.senior.erp.mapper;

import adriane.com.br.senior.erp.entities.Item;
import adriane.com.br.senior.erp.rest.dto.ItemDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Item> itemListDtoToEntityList(List<ItemDto>itemsDto) {
        return itemsDto.stream()
                .map(this::itemDtoToEntity)
                .collect(Collectors.toList());
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

    public List<ItemDto> itemEntityListToListDto(List<Item> items) {
        return items.stream()
                .map(this::itemEntityToDto)
                .collect(Collectors.toList());
    }
}
