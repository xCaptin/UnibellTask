package org.example.unibelltask.annotation;

import org.example.unibelltask.extension.PostgreSQLTestContainersExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для запуска тестового контейнера с Postgresql.
 *
 * @author Kiryl Shynkarou.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(PostgreSQLTestContainersExtension.class)
public @interface PostgresqlTestContainer {

}
