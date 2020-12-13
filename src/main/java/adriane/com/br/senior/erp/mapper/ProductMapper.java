package adriane.com.br.senior.erp.mapper;

import adriane.com.br.senior.erp.entities.Product;
import adriane.com.br.senior.erp.rest.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto productEntityToDto(Product product) {
        if (product == null)
            return  null;

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .isActive(product.getIsActive())
                .isService(product.getIsService())
                .build();
    }

    public Product productDtoToEntity(ProductDto dto) {
        if (dto == null)
            return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setIsActive(dto.getIsActive());
        product.setIsService(dto.getIsService());
        return product;
    }
}
