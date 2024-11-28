package org.example.unibelltask.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.unibelltask.dto.EmailDto;
import org.example.unibelltask.dto.PhoneDto;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients/{clientId}/contacts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContactController {

    ContactService contactService;

    @PostMapping("/phones")
    public ResponseEntity<PhoneDto> addPhoneToClient(@PathVariable UUID clientId,
                                                     @Valid @RequestBody PhoneDto phoneDto) {

        PhoneDto createdPhone = contactService.addPhoneToClient(clientId, phoneDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPhone);
    }

    @PostMapping("/emails")
    public ResponseEntity<EmailDto> addEmailToClient(@PathVariable UUID clientId,
                                                     @Valid @RequestBody EmailDto emailDto) {

        EmailDto createEmail = contactService.addEmailToClient(clientId, emailDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createEmail);
    }

    @GetMapping
    public ResponseEntity<List<Object>> getContactsByClientId(@PathVariable UUID clientId,
                                                              @RequestParam(required = false) String type) {

        if (type != null) {
            List<Object> contactsByTypeAndClientId = contactService.getContactsByTypeAndClientId(clientId, type);
            return ResponseEntity.ok(contactsByTypeAndClientId);
        } else {
            List<Object> contactsByClientId = contactService.getContactsByClientId(clientId);
            return ResponseEntity.ok(contactsByClientId);
        }
    }
}

