package org.example.unibelltask.utils;

import lombok.experimental.UtilityClass;

/**
 * Класс, содержащий константы сообщений об ошибках.
 *
 * @author Kirill Shinkarev.
 */
@UtilityClass
public class ErrorMessages {

    /**
     * Сообщение об ошибке при попытке добавить номер телефона, который уже существует.
     */
    public static final String PHONE_ALREADY_EXISTS = "Номер телефона уже существует.";

    /**
     * Сообщение об ошибке при попытке добавить адрес электронной почты, который уже существует.
     */
    public static final String EMAIL_ALREADY_EXISTS = "Адрес электронной почты уже существует.";

    /**
     * Общее сообщение о нарушении ограничений данных.
     */
    public static final String DATA_CONSTRAINT_VIOLATION = "Нарушение ограничения данных.";

    /**
     * Сообщение об ошибке, когда клиент не найден по-заданному ID.
     * Используйте с помощью {@code String.format}, подставляя идентификатор клиента.
     */
    public static final String CLIENT_NOT_FOUND = "Клиент не найден с ID %s";

    /**
     * Сообщение об общей внутренней ошибке сервера.
     */
    public static final String INTERNAL_SERVER_ERROR = "Внутренняя ошибка сервера";

    /**
     * Сообщение об ошибке при неверном значении параметра запроса.
     * Используйте с помощью {@code String.format}, подставляя имя параметра и его значение.
     */
    public static final String INVALID_PARAMETER_VALUE = "Неверное значение параметра '%s': %s";
}
