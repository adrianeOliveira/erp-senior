package adriane.com.br.senior.erp.factories;

import adriane.com.br.senior.erp.entities.Product;
import adriane.com.br.senior.erp.repositories.ProductRepository;
import br.com.leonardoferreira.jbacon.JBacon;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory extends JBacon<Product> {

    private final Faker faker;

    private final ProductRepository productRepository;

    public ProductFactory(Faker faker, ProductRepository productRepository) {
        this.faker = faker;
        this.productRepository = productRepository;
    }

    @Override
    protected Product getDefault() {
        Product product = new Product();
        product.setName(faker.funnyName().name());
        product.setIsService(faker.random().nextBoolean());
        product.setIsActive(faker.random().nextBoolean());
        product.setPrice(faker.number().randomDouble(2,1, 9));
        return product;
    }

    @Override
    protected Product getEmpty() {
        return new Product();
    }

    @Override
    protected void persist(Product product) {
        productRepository.save(product);
    }
}
