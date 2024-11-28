package org.example.unibelltask.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * {@code JUnit 5} расширение для настройки контейнера {@link PostgreSQLContainer} перед запуском всех тестов.
 *
 * <p>Используется для интеграционных тестов, требующих базы данных PostgreSQL.</p>
 *
 * @author Kiryl Shynkarou
 * @see PostgreSQLContainer
 * @see BeforeAllCallback
 */
public class PostgreSQLTestContainersExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {

        if (!container.isRunning()) {
            container.start();
            System.setProperty("spring.datasource.url", container.getJdbcUrl());
            System.setProperty("spring.datasource.username", container.getUsername());
            System.setProperty("spring.datasource.password", container.getPassword());
        }
    }

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.2-alpine")
        .withInitScript("static/init_schema.sql");
    ;
}
