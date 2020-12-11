package adriane.com.br.senior.erp.entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item {
    private String id;
    private Product product;
    private BigDecimal discount;
    private BigDecimal unitPrice;
    private Integer quantity;
}
