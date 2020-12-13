package adriane.com.br.senior.erp.repositories;

import adriane.com.br.senior.erp.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findByIsActive(Boolean isActive, Pageable pageRequest);

}
