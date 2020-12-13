package adriane.com.br.senior.erp.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class ItemDto implements Serializable {
    private static final long serialVersionUID = -4937640431699256740L;

    private UUID id;
    private ProductDto product;
    private Double discount;
    private Double unitPrice;
    private Integer quantity;
}
