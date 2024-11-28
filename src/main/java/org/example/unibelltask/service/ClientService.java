package org.example.unibelltask.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.unibelltask.dto.ClientDto;
import org.example.unibelltask.entity.Client;
import org.example.unibelltask.mapper.ClientMapper;
import org.example.unibelltask.repository.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientService {

    ClientRepository clientRepository;
    ClientMapper clientMapper;

    /**
     * Создание нового клиента.
     * @param clientDto - DTO с данными клиента.
     * @return - DTO созданного клиента.
     */
    public ClientDto createClient(ClientDto clientDto) {
        log.info("Создание нового клиента: {}", clientDto);

        Client client = clientMapper.toEntity(clientDto);
        Client savedClient = clientRepository.save(client);

        log.info("Клиент успешно сохранен: {}", savedClient);
        return clientMapper.toDto(savedClient);
    }

    /**
     * Получение всех клиентов с учетом пагинации.
     * @param pageable - параметры пагинации.
     * @return - страница DTO клиентов.
     */
    public Page<ClientDto> getAllClients(Pageable pageable) {
        log.info("Получение всех клиентов с пагинацией: {}", pageable);

        Page<Client> clientsPage = clientRepository.findAll(pageable);
        log.info("Найдено клиентов: {}", clientsPage.getTotalElements());

        return clientsPage.map(clientMapper::toDto);
    }

    /**
     * Получение клиента по ID.
     * @param clientId - идентификатор клиента.
     * @return - DTO клиента.
     */
    public ClientDto getClientById(UUID clientId) {
        log.info("Поиск клиента по ID: {}", clientId);

        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> {
                log.error("Клиент не найден с ID: {}", clientId);
                return new RuntimeException("Клиент не найден с ID " + clientId);
            });

        log.info("Клиент найден: {}", client);
        return clientMapper.toDto(client);
    }
}
