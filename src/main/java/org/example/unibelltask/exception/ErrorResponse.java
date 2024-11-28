package org.example.unibelltask.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Класс для представления структуры ответа при возникновении ошибок.
 *
 * @author Kirill Shinkarev.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    /**
     * HTTP-статус ошибки.
     */
    private int status;

    /**
     * Сообщение об ошибке, предоставляющее более подробную информацию.
     */
    private String message;

    /**
     * Временная метка возникновения ошибки.
     */
    private Instant timestamp;
}
