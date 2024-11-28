package org.example.unibelltask.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.unibelltask.dto.EmailDto;
import org.example.unibelltask.dto.PhoneDto;
import org.example.unibelltask.dto.enums.ContactType;
import org.example.unibelltask.entity.Client;
import org.example.unibelltask.entity.Email;
import org.example.unibelltask.entity.Phone;
import org.example.unibelltask.mapper.EmailMapper;
import org.example.unibelltask.mapper.PhoneMapper;
import org.example.unibelltask.repository.ClientRepository;
import org.example.unibelltask.repository.EmailRepository;
import org.example.unibelltask.repository.PhoneRepository;
import org.example.unibelltask.utils.ClientUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис для работы с контактами.
 *
 * @author Kirill Shinkarev.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContactService {

    /**
     * Репозиторий телефона.
     */
    PhoneRepository phoneRepository;

    /**
     * Репозиторий адреса почты.
     */
    EmailRepository emailRepository;

    /**
     * Маппер телефона.
     */
    PhoneMapper phoneMapper;

    /**
     * Маппер адреса почты.
     */
    EmailMapper emailMapper;

    /**
     * Утилитарный класс.
     */
    ClientUtils clientUtils;

    /**
     * Добавление телефона клиенту.
     *
     * @param clientId - ID клиента.
     * @param phoneDto - DTO с данными телефона.
     * @return - DTO сохраненного телефона.
     */
    public PhoneDto addPhoneToClient(UUID clientId, PhoneDto phoneDto) {

        log.info("Добавление телефона для клиента с ID: {}", clientId);

        Client client = clientUtils.findClientById(clientId);

        Phone phone = phoneMapper.toEntity(phoneDto);
        phone.setClient(client);
        Phone savedPhone = phoneRepository.save(phone);

        log.info("Телефон успешно добавлен: {}", savedPhone);
        return phoneMapper.toDto(savedPhone);
    }

    /**
     * Добавление email клиенту.
     *
     * @param clientId - ID клиента.
     * @param emailDto - DTO с данными email.
     * @return - DTO сохраненного email.
     */
    public EmailDto addEmailToClient(UUID clientId, EmailDto emailDto) {

        log.info("Добавление email для клиента с ID: {}", clientId);

        Client client = clientUtils.findClientById(clientId);

        Email email = emailMapper.toEntity(emailDto);
        email.setClient(client);
        Email savedEmail = emailRepository.save(email);

        log.info("Email успешно добавлен: {}", savedEmail);
        return emailMapper.toDto(savedEmail);
    }

    /**
     * Получение всех контактов (телефоны и email) клиента.
     *
     * @param clientId - ID клиента.
     * @return - список контактов.
     */
    public List<Object> getContactsByClientId(UUID clientId) {

        log.info("Получение всех контактов для клиента с ID: {}", clientId);

        Client client = clientUtils.findClientById(clientId);

        List<PhoneDto> phones = phoneRepository.findByClientClientId(clientId).stream()
            .map(phoneMapper::toDto)
            .toList();

        List<EmailDto> emails = emailRepository.findByClientClientId(clientId).stream()
            .map(emailMapper::toDto)
            .toList();

        List<Object> contacts = new ArrayList<>(phones);
        contacts.addAll(emails);

        log.info("Найдено {} телефонов и {} email для клиента с ID: {}", phones.size(), emails.size(), clientId);
        return contacts;
    }

    /**
     * Получение контактов клиента по типу (phone или email).
     *
     * @param clientId - ID клиента.
     * @param type     - тип контакта (phone или email).
     * @return - список контактов указанного типа.
     */
    public List<Object> getContactsByTypeAndClientId(UUID clientId, ContactType type) {

        log.info("Получение контактов типа '{}' для клиента с ID: {}", type, clientId);

        Client client = clientUtils.findClientById(clientId);

        switch (type) {
            case PHONE:
                List<Object> phones = phoneRepository.findByClientClientId(clientId).stream()
                    .map(phoneMapper::toDto)
                    .collect(Collectors.toList());
                log.info("Найдено телефонов: {}", phones.size());
                return phones;
            case EMAIL:
                List<Object> emails = emailRepository.findByClientClientId(clientId).stream()
                    .map(emailMapper::toDto)
                    .collect(Collectors.toList());
                log.info("Найдено email: {}", emails.size());
                return emails;
            default:
                log.error("Неверный тип контакта: {}", type);
                throw new IllegalArgumentException("Неверный тип контакта: " + type);
        }
    }
}
