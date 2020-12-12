package adriane.com.br.senior.erp.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private UUID id;
    private String name;
    private Double price;
    private Boolean isService;
    private Boolean isActive;
}
