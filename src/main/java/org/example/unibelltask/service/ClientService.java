package org.example.unibelltask.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.unibelltask.dto.ClientDto;
import org.example.unibelltask.entity.Client;
import org.example.unibelltask.mapper.ClientMapper;
import org.example.unibelltask.repository.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientService {

    ClientRepository clientRepository;

    ClientMapper clientMapper;

    public ClientDto createClient(ClientDto clientDto) {

        Client client = clientMapper.toEntity(clientDto);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDto(savedClient);
    }

    public Page<ClientDto> getAllClients(Pageable pageable) {

        Page<Client> clientsPage = clientRepository.findAll(pageable);
        return clientsPage.map(clientMapper::toDto);
    }

    public ClientDto getClientById(UUID clientId) {

        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Клиент не найден с id " + clientId));
        return clientMapper.toDto(client);
    }
}
