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
import lombok.extern.slf4j.Slf4j;
import org.example.unibelltask.dto.ClientDto;
import org.example.unibelltask.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Реализация контроллера для работы с клиентами.
 *
 * @author Kirill Shinkarev.
 */
@Slf4j
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Client API", description = "API для управления клиентами")
public class ClientController {

    ClientService clientService;

    @Operation(
        summary = "Создание нового клиента",
        description = "Создает клиента на основе переданных данных и возвращает созданного клиента.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Клиент успешно создан",
                         content = @Content(schema = @Schema(implementation = ClientDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
        }
    )
    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto) {

        log.info("Запрос на создание клиента: {}", clientDto);
        ClientDto createdClient = clientService.createClient(clientDto);
        log.info("Клиент успешно создан: {}", createdClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @Operation(
        summary = "Получение клиента по ID",
        description = "Возвращает информацию о клиенте по его идентификатору.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Информация о клиенте успешно получена",
                         content = @Content(schema = @Schema(implementation = ClientDto.class))),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable UUID id) {

        log.info("Запрос на получение клиента по ID: {}", id);
        ClientDto clientDto = clientService.getClientById(id);
        log.info("Клиент найден: {}", clientDto);
        return ResponseEntity.ok(clientDto);
    }

    @Operation(
        summary = "Получение списка всех клиентов",
        description = "Возвращает список всех клиентов с поддержкой пагинации.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Список клиентов успешно получен",
                         content = @Content(mediaType = "application/json"))
        }
    )
    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients(Pageable pageable) {

        log.info("Запрос на получение всех клиентов с пагинацией: {}", pageable);
        Page<ClientDto> clientsPage = clientService.getAllClients(pageable);
        log.info("Найдено клиентов: {}", clientsPage.getTotalElements());
        return ResponseEntity.ok(clientsPage.getContent());
    }
}
