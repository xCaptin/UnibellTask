package org.example.unibelltask.exception;

/**
 * Исключение, указывающее, что запрашиваемый ресурс не найден.
 *
 * @author Kirill Shinkarev.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Создает новое исключение с указанным сообщением.
     *
     * @param message сообщение об ошибке
     */
    public ResourceNotFoundException(String message) {

        super(message);
    }
}
