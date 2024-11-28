package org.example.unibelltask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDto(
    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Некорректный формат email")
    String emailAddress
) implements ContactDto {

}
