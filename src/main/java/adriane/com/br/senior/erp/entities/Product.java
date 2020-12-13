package adriane.com.br.senior.erp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Product implements Serializable {
    private static final long serialVersionUID = -1L;
    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private UUID id;

    private String name;

    private Double price;

    private Boolean isService;

    private Boolean isActive;
}
