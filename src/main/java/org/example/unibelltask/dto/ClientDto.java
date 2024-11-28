package org.example.unibelltask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ClientDto(
    @NotBlank(message = "Имя не должно быть пустым")
    String firstName,

    @NotBlank(message = "Фамилия не должна быть пустой")
    String lastName
) {

}
