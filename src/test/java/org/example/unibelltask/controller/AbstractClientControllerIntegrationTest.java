package org.example.unibelltask.controller;

import org.example.unibelltask.annotation.PostgresqlTestContainer;
import org.example.unibelltask.mapper.ClientMapper;
import org.example.unibelltask.mapper.EmailMapper;
import org.example.unibelltask.mapper.PhoneMapper;
import org.example.unibelltask.repository.ClientRepository;
import org.example.unibelltask.repository.EmailRepository;
import org.example.unibelltask.repository.PhoneRepository;
import org.example.unibelltask.service.ClientService;
import org.example.unibelltask.service.ContactService;
import org.example.unibelltask.utils.ClientUtils;
import org.example.unibelltask.utils.EntityGeneratorUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
// @DirtiesContext
@PostgresqlTestContainer
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractClientControllerIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ClientService clientService;

    @Autowired
    protected ContactService contactService;

    @Autowired
    protected ClientMapper clientMapper;

    @Autowired
    protected EmailMapper emailMapper;

    @Autowired
    protected PhoneMapper phoneMapper;

    @Autowired
    protected ClientRepository clientRepository;

    @Autowired
    protected EmailRepository emailRepository;

    @Autowired
    protected PhoneRepository phoneRepository;

    @Autowired
    protected ClientUtils clientUtils;

    @Autowired
    private EntityGeneratorUtils entityGeneratorUtils;

    @BeforeEach
    void setUp() {

        entityGeneratorUtils.generateTestData(15);
    }

    @AfterEach
    void deleteAll() {

        clientRepository.deleteAll();
        emailRepository.deleteAll();
        phoneRepository.deleteAll();
    }
}
