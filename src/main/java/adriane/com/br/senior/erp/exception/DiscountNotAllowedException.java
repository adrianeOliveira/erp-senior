package adriane.com.br.senior.erp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DiscountNotAllowedException extends ResponseStatusException {

    public DiscountNotAllowedException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

}
