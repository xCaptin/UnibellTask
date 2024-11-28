package org.example.unibelltask.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.unibelltask.dto.EmailDto;
import org.example.unibelltask.dto.PhoneDto;
import org.example.unibelltask.entity.Client;
import org.example.unibelltask.entity.Email;
import org.example.unibelltask.entity.Phone;
import org.example.unibelltask.mapper.EmailMapper;
import org.example.unibelltask.mapper.PhoneMapper;
import org.example.unibelltask.repository.ClientRepository;
import org.example.unibelltask.repository.EmailRepository;
import org.example.unibelltask.repository.PhoneRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContactService {

    ClientRepository clientRepository;

    PhoneRepository phoneRepository;

    EmailRepository emailRepository;

    PhoneMapper phoneMapper;

    EmailMapper emailMapper;

    public PhoneDto addPhoneToClient(UUID clientId, PhoneDto phoneDto) {

        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Клиент не найден с id " + clientId));
        Phone phone = phoneMapper.toEntity(phoneDto);
        phone.setClient(client);
        Phone savedPhone = phoneRepository.save(phone);
        return phoneMapper.toDto(savedPhone);
    }

    public EmailDto addEmailToClient(UUID clientId, EmailDto emailDto) {

        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Клиент не найден с id " + clientId));
        Email email = emailMapper.toEntity(emailDto);
        email.setClient(client);
        Email savedEmail = emailRepository.save(email);
        return emailMapper.toDto(savedEmail);
    }

    public List<Object> getContactsByClientId(UUID clientId) {

        List<PhoneDto> phones = phoneRepository.findByClientClientId(clientId).stream()
            .map(phoneMapper::toDto)
            .toList();

        List<EmailDto> emails = emailRepository.findByClientClientId(clientId).stream()
            .map(emailMapper::toDto)
            .toList();

        List<Object> contacts = new ArrayList<>(phones);
        contacts.addAll(emails);
        return contacts;
    }

    public List<Object> getContactsByTypeAndClientId(UUID clientId, String type) {

        if (type.equalsIgnoreCase("phone")) {
            return phoneRepository.findByClientClientId(clientId).stream()
                .map(phoneMapper::toDto)
                .collect(Collectors.toList());
        } else if (type.equalsIgnoreCase("email")) {
            return emailRepository.findByClientClientId(clientId).stream()
                .map(emailMapper::toDto)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Неверный тип контакта: " + type);
        }
    }
}
