package org.example.unibelltask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.unibelltask.dto.EmailDto;
import org.example.unibelltask.dto.PhoneDto;
import org.example.unibelltask.dto.enums.ContactType;
import org.example.unibelltask.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Реализация контроллера для работы с контактами.
 *
 * @author Kirill Shinkarev.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/clients/{clientId}/contacts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Contact API", description = "API для управления контактами клиентов")
public class ContactController {

    ContactService contactService;

    @Operation(
        summary = "Добавление телефона клиенту",
        description = "Добавляет телефон клиенту на основе переданных данных.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Телефон успешно добавлен",
                         content = @Content(schema = @Schema(implementation = PhoneDto.class))),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
        }
    )
    @PostMapping("/phones")
    public ResponseEntity<PhoneDto> addPhoneToClient(@PathVariable UUID clientId,
                                                     @Valid @RequestBody PhoneDto phoneDto) {

        PhoneDto createdPhone = contactService.addPhoneToClient(clientId, phoneDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPhone);
    }

    @Operation(
        summary = "Добавление email клиенту",
        description = "Добавляет email клиенту на основе переданных данных.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Email успешно добавлен",
                         content = @Content(schema = @Schema(implementation = EmailDto.class))),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
        }
    )
    @PostMapping("/emails")
    public ResponseEntity<EmailDto> addEmailToClient(@PathVariable UUID clientId,
                                                     @Valid @RequestBody EmailDto emailDto) {

        EmailDto createdEmail = contactService.addEmailToClient(clientId, emailDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmail);
    }

    @Operation(
        summary = "Получение контактов клиента",
        description = "Возвращает список всех контактов клиента или контактов указанного типа (phone или email).",
        responses = {
            @ApiResponse(responseCode = "200", description = "Список контактов успешно получен")
        }
    )
    @GetMapping
    public ResponseEntity<List<Object>> getContactsByClientId(
        @PathVariable UUID clientId,
        @RequestParam(required = false) ContactType type
    ) {

        List<Object> contacts;
        if (type != null) {
            contacts = contactService.getContactsByTypeAndClientId(clientId, type);
        } else {
            contacts = contactService.getContactsByClientId(clientId);
        }
        return ResponseEntity.ok(contacts);
    }

}
