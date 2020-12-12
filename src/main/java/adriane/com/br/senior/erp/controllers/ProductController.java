package adriane.com.br.senior.erp.controllers;

import adriane.com.br.senior.erp.entities.Product;
import adriane.com.br.senior.erp.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> listProducts() {
        log.info("M=listProducts, I=Buscando todos os Produtos ativos");
        return productService.listProducts(true);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody final Product product) {
        log.info("M=createProduct, product = {}", product);
        return productService.saveProduct(product);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable UUID productId, @RequestBody Product product) {
        log.info("M=updateProduct, productId = {}, product = {}", productId, product);
        productService.updateProduct(productId, product);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable final UUID productId)  {
        log.info("M=deleteProduct, productId ={}", productId);
        productService.deleteProduct(productId);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Product findProductById(@PathVariable final UUID productId) {
        log.info("M=findProductById, productId = {}", productId);
        return productService.findProductById(productId);
    }

}
