package org.example.unibelltask.exception;

import jakarta.validation.ConstraintViolation;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * Глобальный обработчик исключений для обработки различных типов ошибок в приложении.
 *
 * @author Kirill Shinkarev.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработчик исключений нарушения целостности данных.
     *
     * @param ex      исключение {@link DataIntegrityViolationException}.
     * @param request объект {@link WebRequest}, содержащий информацию о запросе.
     * @return объект {@link ErrorResponse}, содержащий информацию об ошибке.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {

        String message = getConstraintViolationMessage(ex);
        return new ErrorResponse(HttpStatus.CONFLICT.value(), message, Instant.now());
    }

    /**
     * Получение сообщения об ошибке на основе нарушения ограничения данных.
     *
     * @param ex исключение {@link DataIntegrityViolationException}.
     * @return строка с сообщением об ошибке.
     */
    private String getConstraintViolationMessage(DataIntegrityViolationException ex) {

        Throwable cause = ex.getCause();
        while (cause != null) {
            if (cause instanceof ConstraintViolationException) {
                ConstraintViolationException constraintEx = (ConstraintViolationException) cause;
                String constraintName = constraintEx.getConstraintName();

                if (constraintName != null) {
                    switch (constraintName) {
                        case "uq_phone_number":
                            return "Номер телефона уже существует.";
                        case "uq_email_address":
                            return "Адрес электронной почты уже существует.";
                        default:
                            return "Нарушение ограничения данных: " + constraintName;
                    }
                } else {
                    return "Нарушение ограничения данных.";
                }
            }
            cause = cause.getCause();
        }
        return "Нарушение ограничения данных.";
    }

    /**
     * Обработчик исключений валидации аргументов методов.
     *
     * @param ex исключение {@link MethodArgumentNotValidException}.
     * @return объект {@link ErrorResponse}, содержащий информацию об ошибке.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, Instant.now());
    }

    /**
     * Обработчик исключений валидации ограничений (ConstraintViolationException).
     *
     * @param ex исключение {@link jakarta.validation.ConstraintViolationException}.
     * @return объект {@link ErrorResponse}, содержащий информацию об ошибке.
     */
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(jakarta.validation.ConstraintViolationException ex) {

        String message = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, Instant.now());
    }

    /**
     * Обрабатывает исключения типа {@link ResourceNotFoundException}, возвращая статус 404 Not Found.
     *
     * @param ex исключение ResourceNotFoundException
     * @return объект {@link ErrorResponse} с информацией об ошибке
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {

        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now());
    }

    /**
     * Обрабатывает исключения типа {@link MethodArgumentTypeMismatchException}, возвращая статус 400 Bad Request.
     * Вызывается при неверном значении параметра запроса, например, при неправильном типе данных.
     *
     * @param ex исключение MethodArgumentTypeMismatchException
     * @return объект {@link ErrorResponse} с информацией об ошибке
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        String message = String.format("Неверное значение параметра '%s': %s", ex.getName(), ex.getValue());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, Instant.now());
    }

    /**
     * Обработчик всех необработанных исключений.
     *
     * @param ex исключение {@link Exception}.
     * @return объект {@link ErrorResponse}, содержащий информацию об ошибке.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAllExceptions(Exception ex) {

        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Внутренняя ошибка сервера", Instant.now());
    }
}
