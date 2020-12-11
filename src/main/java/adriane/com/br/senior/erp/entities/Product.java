package adriane.com.br.senior.erp.entities;

import lombok.Data;

@Data
public class Product {
    private String id;
    private String name;
    private Boolean isService;
    private Boolean ativo;
}
