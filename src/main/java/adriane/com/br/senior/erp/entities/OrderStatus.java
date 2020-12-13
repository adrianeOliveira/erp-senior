package adriane.com.br.senior.erp.entities;

public enum OrderStatus {
    ABERTA("ABERTA"),
    PROCESSANDO("PROCESSANDO"),
    FECHADA("FECHADA");

    private String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
