package adriane.com.br.senior.erp.service;

import adriane.com.br.senior.erp.entities.Product;
import adriane.com.br.senior.erp.exception.ProductNotFoundException;
import adriane.com.br.senior.erp.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product saveProduct(final Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(final UUID id, final Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        } else {
            throw new ProductNotFoundException();
        }
    }

    @Transactional
    public void deleteProduct(final UUID id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Product findProductById(final UUID id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Product> listProducts(final Boolean isActive) {
        return productRepository.findByIsActive(true);
    }

}
