package adriane.com.br.senior.erp.repositories;

import adriane.com.br.senior.erp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByIsActive(Boolean isActive);
}
