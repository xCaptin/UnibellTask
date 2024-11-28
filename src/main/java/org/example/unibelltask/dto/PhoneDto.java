package org.example.unibelltask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO для передачи данных телефона.
 *
 * @author Kirill Shinkarev.
 */
@Schema(description = "DTO для представления номера телефона клиента")
public record PhoneDto(

    @NotBlank(message = "Номер телефона не должен быть пустым")
    @Pattern(
        regexp = "\\+7\\d{10}|\\+375\\d{9}",
        message = "Некорректный формат номера телефона. Ожидается формат +7XXXXXXXXXX или +375XXXXXXXXX"
    )
    @Schema(description = "Номер телефона клиента", example = "+71234567890")
    String phoneNumber
) implements ContactDto {

}
