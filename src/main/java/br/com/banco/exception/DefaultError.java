package br.com.banco.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Default error type
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultError {
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
