package org.example.unibelltask.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.unibelltask.entity.Client;
import org.example.unibelltask.exception.ResourceNotFoundException;
import org.example.unibelltask.repository.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Утилитный класс для работы с клиентами.
 *
 * @author Kirill Shinkarev.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientUtils {

    /**
     * Репозиторий для работы с клиентами.
     */
    ClientRepository clientRepository;

    /**
     * Находит клиента по идентификатору или выбрасывает исключение, если клиент не найден.
     *
     * @param clientId идентификатор клиента
     * @return найденный клиент
     * @throws ResourceNotFoundException если клиент не найден
     */
    public Client findClientById(UUID clientId) {

        return clientRepository.findById(clientId)
            .orElseThrow(() -> {
                log.error("Клиент не найден с ID: {}", clientId);
                return new ResourceNotFoundException("Клиент не найден с ID " + clientId);
            });
    }
}
