package adriane.com.br.senior.erp.entities;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private String id;
    private List<Item> items;
    private OrderStatus orderStatus;
}
