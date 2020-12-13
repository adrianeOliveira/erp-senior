package adriane.com.br.senior.erp.factories;

import adriane.com.br.senior.erp.mapper.ItemMapper;
import adriane.com.br.senior.erp.mapper.ProductMapper;
import adriane.com.br.senior.erp.repositories.ItemRepository;
import adriane.com.br.senior.erp.repositories.ProductRepository;
import adriane.com.br.senior.erp.rest.dto.ItemDto;
import adriane.com.br.senior.erp.rest.dto.ProductDto;
import br.com.leonardoferreira.jbacon.JBacon;
import br.com.leonardoferreira.jbacon.annotation.JBaconTemplate;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ItemDtoFactory extends JBacon<ItemDto> {

    private final ProductFactory productFactory;

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    private final Faker faker;

    public ItemDtoFactory(ProductFactory productFactory, ProductRepository productRepository,
                          ProductMapper productMapper,
                          ItemRepository itemRepository,
                          ItemMapper itemMapper,
                          Faker faker) {
        this.productFactory = productFactory;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.faker = faker;
    }

    @Override
    protected ItemDto getDefault() {

        return ItemDto.builder()
                .product(productFactory.getEmpty())
                .quantity(faker.number().randomDigitNotZero())
                .discount(faker.number().randomDouble(2,1,9))
                .unitPrice(faker.number().randomDouble(2,1,9))
                .build();
    }

    @Override
    protected ItemDto getEmpty() {
        return ItemDto.builder().build();
    }

    @Override
    protected void persist(ItemDto item) {
        itemRepository.save(itemMapper.itemDtoToEntity(item));
    }

    @JBaconTemplate("itemDtoWithProduct")
    protected ItemDto buildItemWIthProduct() {
        ProductDto productDto = productFactory.build();
        UUID productId = productRepository.save(productMapper.productDtoToEntity(productDto)).getId();
        productDto.setId(productId);
        ItemDto itemDto = this.getDefault();
        itemDto.setProduct(productDto);
        return itemDto;
    }

}
