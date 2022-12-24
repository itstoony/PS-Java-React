package br.com.banco.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import java.time.DateTimeException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Exception handler
 */
@RestControllerAdvice
public class ContaControllerAdvice {

    /**
     * Date time exception response entity.
     *
     * @param ex Exception
     * @param request request
     * @return the response entity
     */
    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<?> dateTimeException(DateTimeException ex, HttpServletRequest request) {

        var error = DefaultError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    /**
     * SaldoInsuficienteException response entity
     *
     * @param ex Exception
     * @param request request
     * @return the response entity
     */
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<?> saldoException(SaldoInsuficienteException ex, HttpServletRequest request) {
        var error = DefaultError.builder()
                .timestamp(System.currentTimeMillis())
                .status(BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    /**
     * Illegal argument exception response entity.
     *
     * @param ex Exception
     * @param request request
     * @return the response entity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {

        var error = DefaultError.builder()
                .timestamp(System.currentTimeMillis())
                .status(BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    /**
     * Object not found exception response entity.
     *
     * @param ex Exception
     * @param request request
     * @return the response entity
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<?> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request) {

        var error = DefaultError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(error);
    }

}
