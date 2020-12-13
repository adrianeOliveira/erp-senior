package adriane.com.br.senior.erp.service;

import adriane.com.br.senior.erp.entities.Product;
import adriane.com.br.senior.erp.exception.ProductNotFoundException;
import adriane.com.br.senior.erp.mapper.ProductMapper;
import adriane.com.br.senior.erp.repositories.ProductRepository;
import adriane.com.br.senior.erp.rest.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductDto saveProduct(final ProductDto productDto) {
        Product product = productMapper.productDtoToEntity(productDto);
        return productMapper.productEntityToDto(productRepository.save(product));
    }

    @Transactional
    public void updateProduct(final UUID id, final ProductDto productDto) {
        if (productRepository.existsById(id)) {
            productDto.setId(id);
            Product product = productMapper.productDtoToEntity(productDto);
            productRepository.save(product);
        } else {
            throw new ProductNotFoundException();
        }
    }

    @Transactional
    public void deleteProduct(final UUID id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ProductDto findProductById(final UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        return productMapper.productEntityToDto(product);
    }

    @Transactional(readOnly = true)
    public Page<Product> listProducts(final Boolean isActive, Pageable pageRequest) {
        return productRepository.findByIsActive(isActive, pageRequest);
    }

    private Pageable createPageRequest() {
        return PageRequest.of(0, 10);
    }
}
