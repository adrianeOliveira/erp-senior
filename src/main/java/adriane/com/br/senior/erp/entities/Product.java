package adriane.com.br.senior.erp.entities;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
public class Product {
    @Id
    @Type(type = "pg-uuid")
    @Column(name = "product_id")
    private UUID id;
    private String name;
    private Double price;
    private Boolean isService;
    private Boolean isActive;
}
