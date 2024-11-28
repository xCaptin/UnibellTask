package org.example.unibelltask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO для передачи данных email.
 *
 * @author Kirill Shinkarev.
 */
@Schema(description = "DTO для представления email клиента")
public record EmailDto(

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Некорректный формат email")
    @Schema(description = "Адрес электронной почты клиента", example = "example@mail.com")
    String emailAddress
) implements ContactDto {

}
