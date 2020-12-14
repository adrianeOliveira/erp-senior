package adriane.com.br.senior.erp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductNotAllowedException extends ResponseStatusException {

    public ProductNotAllowedException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
