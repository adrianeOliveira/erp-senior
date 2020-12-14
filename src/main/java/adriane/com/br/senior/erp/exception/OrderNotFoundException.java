package adriane.com.br.senior.erp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderNotFoundException extends ResponseStatusException {
    public OrderNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
