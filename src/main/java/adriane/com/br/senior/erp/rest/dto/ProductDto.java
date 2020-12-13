package adriane.com.br.senior.erp.rest.dto;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class ProductDto implements Serializable {
    private static final long serialVersionUID = 7335701066624642325L;

    private UUID id;
    private String name;
    private Double price;
    private Boolean isService;
    private Boolean isActive;
}
