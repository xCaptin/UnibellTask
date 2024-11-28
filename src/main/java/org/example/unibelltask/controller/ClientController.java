package org.example.unibelltask.controller;

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

@Slf4j
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientController {

    ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto) {
        ClientDto createdClient = clientService.createClient(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable UUID id) {
        ClientDto clientDto = clientService.getClientById(id);
        return ResponseEntity.ok(clientDto);
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients(Pageable pageable) {
        Page<ClientDto> clientsPage = clientService.getAllClients(pageable);
        return ResponseEntity.ok(clientsPage.getContent());
    }
}
