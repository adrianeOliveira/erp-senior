package adriane.com.br.senior.erp.factories;

import adriane.com.br.senior.erp.mapper.ProductMapper;
import adriane.com.br.senior.erp.repositories.ProductRepository;
import adriane.com.br.senior.erp.rest.dto.ProductDto;
import br.com.leonardoferreira.jbacon.JBacon;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory extends JBacon<ProductDto> {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final Faker faker;

    public ProductFactory(ProductRepository productRepository,
                          ProductMapper productMapper,
                          Faker faker) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.faker = faker;
    }

    @Override
    protected ProductDto getDefault() {
        return ProductDto.builder()
                .name(faker.funnyName().name())
                .price(faker.number().randomDouble(2,1,9))
                .isActive(true)
                .isService(faker.random().nextBoolean())
                .build();
    }

    @Override
    protected ProductDto getEmpty() {
        return ProductDto.builder().build();
    }

    @Override
    protected void persist(ProductDto product) {
        productRepository.save(productMapper.productDtoToEntity(product));
    }
}
