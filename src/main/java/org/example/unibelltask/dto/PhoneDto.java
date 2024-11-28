package org.example.unibelltask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PhoneDto(
    @NotBlank(message = "Номер телефона не должен быть пустым")
    @Pattern(
        regexp = "\\+7\\d{10}|\\+375\\d{9}",
        message = "Некорректный формат номера телефона. Ожидается формат +7XXXXXXXXXX или +375XXXXXXXXX"
    )
    String phoneNumber
) implements ContactDto {

}
