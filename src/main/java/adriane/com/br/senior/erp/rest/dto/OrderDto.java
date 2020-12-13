package adriane.com.br.senior.erp.rest.dto;

import adriane.com.br.senior.erp.entities.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderDto implements Serializable {
    private static final long serialVersionUID = 6960895280807925595L;

    private UUID id;
    private List<ItemDto> items;
    private OrderStatus status;
}
