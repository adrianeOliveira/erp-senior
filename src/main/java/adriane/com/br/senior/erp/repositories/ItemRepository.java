package adriane.com.br.senior.erp.repositories;

import adriane.com.br.senior.erp.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    List<Item> findItemByOrderId(UUID orderId);

    Integer countByProductId(UUID productId);
}
