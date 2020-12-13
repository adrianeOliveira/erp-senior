package adriane.com.br.senior.erp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,
        reason = "Não é permitido pedidos com produtos desativados!")
public class ProductNotAllowedException extends RuntimeException{
}
