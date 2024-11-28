package org.example.unibelltask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * DTO для передачи информации о клиенте.
 *
 * @author Kirill Shinkarev.
 */
@Builder
@Schema(description = "DTO для представления данных клиента")
public record ClientDto(

    @NotBlank(message = "Имя не должно быть пустым")
    @Schema(description = "Имя клиента", example = "Иван")
    String firstName,

    @NotBlank(message = "Фамилия не должна быть пустой")
    @Schema(description = "Фамилия клиента", example = "Иванов")
    String lastName
) {

}
