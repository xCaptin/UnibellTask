package org.example.unibelltask.controller;

import org.example.unibelltask.entity.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Интеграционные тесты для ContactController")
class ContactControllerIntegrationTest extends AbstractClientControllerIntegrationTest {

    private final String BASE_URL = "/clients";

    @Test
    @DisplayName("Успешное добавление телефона клиенту")
    void testAddPhoneToClient_Success() throws Exception {
        Client client = clientRepository.findAll().get(0);
        UUID clientId = client.getClientId();
        String phoneJson = "{ \"phoneNumber\": \"+375333595996\" }";

        ResultActions result = mockMvc.perform(post(BASE_URL + "/" + clientId + "/contacts/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(phoneJson));

        result.andExpect(status().isCreated())
            .andExpect(jsonPath("$.phoneNumber").value("+375333595996"));
    }

    @Test
    @DisplayName("Ошибка при добавлении телефона несуществующему клиенту")
    void testAddPhoneToClient_ClientNotFound() throws Exception {
        UUID nonExistentClientId = UUID.randomUUID();
        String phoneJson = "{ \"phoneNumber\": \"+375333595996\" }";

        ResultActions result = mockMvc.perform(post(BASE_URL + "/" + nonExistentClientId + "/contacts/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(phoneJson));

        result.andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Клиент не найден с ID " + nonExistentClientId));
    }

    @Test
    @DisplayName("Ошибка при добавлении дублирующегося номера телефона")
    void testAddPhoneToClient_DuplicatePhoneNumber() throws Exception {
        Client client = clientRepository.findAll().get(0);
        UUID clientId = client.getClientId();
        String phoneNumber = "+375333595996";
        String phoneJson = "{ \"phoneNumber\": \"" + phoneNumber + "\" }";

        // Первое добавление номера телефона
        mockMvc.perform(post(BASE_URL + "/" + clientId + "/contacts/phones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(phoneJson))
            .andExpect(status().isCreated());

        // Попытка добавить тот же номер телефона снова
        ResultActions result = mockMvc.perform(post(BASE_URL + "/" + clientId + "/contacts/phones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(phoneJson));

        result.andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value("Номер телефона уже существует."));
    }

    @Test
    @DisplayName("Успешное получение контактов клиента")
    void testGetContactsByClientId_Success() throws Exception {
        Client client = clientRepository.findAll().get(0);
        UUID clientId = client.getClientId();

        ResultActions result = mockMvc.perform(get(BASE_URL + "/" + clientId + "/contacts"));

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$", not(empty())))
            .andExpect(jsonPath("$[*].phoneNumber", hasItem(notNullValue())))
            .andExpect(jsonPath("$[*].emailAddress", hasItem(notNullValue())));
    }

    @Test
    @DisplayName("Ошибка при попытке получить контакты несуществующего клиента")
    void testGetContactsByClientId_ClientNotFound() throws Exception {
        UUID nonExistentClientId = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get(BASE_URL + "/" + nonExistentClientId + "/contacts"));

        result.andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Клиент не найден с ID " + nonExistentClientId));
    }

    @Test
    @DisplayName("Успешное получение контактов клиента с фильтром по типу")
    void testGetContactsByClientId_TypeFilter() throws Exception {
        Client client = clientRepository.findAll().get(0);
        UUID clientId = client.getClientId();

        ResultActions result = mockMvc.perform(get(BASE_URL + "/" + clientId + "/contacts")
            .param("type", "PHONE"));

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$", not(empty())))
            .andExpect(jsonPath("$[*].phoneNumber", everyItem(notNullValue())))
            .andExpect(jsonPath("$[*].emailAddress").doesNotExist());
    }

    @Test
    @DisplayName("Ошибка при указании некорректного типа контакта")
    void testGetContactsByClientId_InvalidType() throws Exception {
        Client client = clientRepository.findAll().get(0);
        UUID clientId = client.getClientId();

        ResultActions result = mockMvc.perform(get(BASE_URL + "/" + clientId + "/contacts")
            .param("type", "INVALID"));

        result.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Неверное значение параметра 'type': INVALID"));
    }

    @Test
    @DisplayName("Ошибка при добавлении некорректного email клиенту")
    void testAddEmailToClient_InvalidEmail() throws Exception {
        Client client = clientRepository.findAll().get(0);
        UUID clientId = client.getClientId();
        String emailJson = "{ \"emailAddress\": \"invalid-email\" }";

        ResultActions result = mockMvc.perform(post(BASE_URL + "/" + clientId + "/contacts/emails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(emailJson));

        result.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Некорректный формат email"));
    }
}
