package org.example.unibelltask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Точка запуска приложения.
 *
 * @author Kirill Shinkarev.
 */
@SpringBootApplication
public class UnibellTaskApplication {

    /**
     * Запускает приложение.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {

        SpringApplication.run(UnibellTaskApplication.class, args);
    }
}
