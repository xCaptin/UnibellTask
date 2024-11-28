package org.example.unibelltask.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Интеграционные тесты для ClientController")
class ClientControllerIntegrationTest extends AbstractClientControllerIntegrationTest {

    private final String BASE_URL = "/clients";

    @Test
    @DisplayName("Успешное создание клиента")
    void testCreateClient_Success() throws Exception {
        String clientJson = "{ \"firstName\": \"John\", \"lastName\": \"Doe\" }";

        ResultActions result = mockMvc.perform(post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(clientJson));

        result.andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstName").value("John"))
            .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    @DisplayName("Ошибка при создании клиента с некорректными данными")
    void testCreateClient_InvalidInput() throws Exception {
        String clientJson = "{ \"firstName\": \"\", \"lastName\": \"\" }";

        ResultActions result = mockMvc.perform(post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(clientJson));

        result.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    @DisplayName("Успешное получение клиента по ID")
    void testGetClientById_Success() throws Exception {
        UUID clientId = clientRepository.findAll().get(0).getClientId();

        ResultActions result = mockMvc.perform(get(BASE_URL + "/" + clientId));

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").isNotEmpty())
            .andExpect(jsonPath("$.lastName").isNotEmpty());
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
    @DisplayName("Успешное получение списка всех клиентов")
    void testGetAllClients_Success() throws Exception {
        ResultActions result = mockMvc.perform(get(BASE_URL));

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThan(0))))
            .andExpect(jsonPath("$[0].firstName").isNotEmpty())
            .andExpect(jsonPath("$[0].lastName").isNotEmpty());
    }

    @Test
    @DisplayName("Успешное получение клиентов с пагинацией")
    void testGetAllClients_WithPagination() throws Exception {
        ResultActions result = mockMvc.perform(get(BASE_URL)
            .param("page", "0")
            .param("size", "5"));

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(5)));
    }
}
