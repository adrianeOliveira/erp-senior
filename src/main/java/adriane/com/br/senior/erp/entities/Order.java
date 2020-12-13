package adriane.com.br.senior.erp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "order_table")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
