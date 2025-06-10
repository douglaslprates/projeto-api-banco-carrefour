package com.serverest.api.tests;

import com.serverest.api.clients.AuthClient;
import com.serverest.api.clients.UserClient;
import com.serverest.api.models.UserDTO;
import com.serverest.api.utils.TestDataGenerator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@Epic("User API")
@Feature("CRUD Operations")
public class UserCrudTest {
    private static String authToken;
    private static String userId;
    private static UserDTO createdUser;

    @BeforeAll
    public static void setup() {
        authToken = AuthClient.getAuthToken("fulano@qa.com", "teste");
        createdUser = TestDataGenerator.generateRandomUser();

        Response response = UserClient.createUser(createdUser, authToken);
        userId = response.jsonPath().getString("_id");
    }

    @Test
    @Order(1)
    @DisplayName("GET /usuarios - Listar todos os usuários")
    @Severity(SeverityLevel.NORMAL)
    void testGetAllUsers() {
        Response response = UserClient.getAllUsers(authToken);

        assertEquals(200, response.getStatusCode());

        int quantidade = response.jsonPath().getInt("quantidade");
        List<Map<String, Object>> usuarios = response.jsonPath().getList("usuarios");

        assertTrue(quantidade >= 1, "Deveria retornar pelo menos 1 usuário");
        assertFalse(usuarios.isEmpty(), "Lista de usuários não deveria estar vazia");
    }

    @Test
    @Order(2)
    @DisplayName("Criar usuário com sucesso")
    @Severity(SeverityLevel.BLOCKER)
    void testCreateUser() {
        UserDTO user = TestDataGenerator.generateRandomUser();
        Response response = UserClient.createUser(user, authToken);

        assertEquals(201, response.getStatusCode());
        assertNotNull(response.jsonPath().getString("_id"));
        userId = response.jsonPath().getString("_id"); // Salva para testes seguintes
    }

    @Test
    @Order(3)
    @DisplayName("GET /usuarios/{id} - Consultar usuário específico")
    @Severity(SeverityLevel.CRITICAL)
    void testGetUser() {
        Response response = UserClient.getUser(userId, authToken);

        assertEquals(200, response.getStatusCode());
        assertEquals(createdUser.getNome(), response.jsonPath().getString("nome"));
        assertEquals(createdUser.getEmail(), response.jsonPath().getString("email"));
    }

    @Test
    @Order(4)
    @DisplayName("PUT /usuarios/{id} - Atualizar usuário completo")
    @Severity(SeverityLevel.CRITICAL)
    void testUpdateUser() {
        UserDTO updatedUser = UserDTO.builder()
                .nome("Nome Atualizado")
                .email("atualizado@gmail.com")
                .password("novaSenha123")
                .administrador("false")
                .build();

        Response updateResponse = UserClient.updateUser(userId, updatedUser, authToken);
        Response getResponse = UserClient.getUser(userId, authToken);

        assertAll("Atualização de usuário",
                () -> assertEquals(200, updateResponse.getStatusCode()),
                () -> assertEquals("Registro alterado com sucesso",
                        updateResponse.jsonPath().getString("message")),
                () -> assertEquals("false", getResponse.jsonPath().getString("administrador"))
        );
    }

    @Test
    @Order(5)
    @DisplayName("DELETE /usuarios/{id} - Remover usuário")
    @Severity(SeverityLevel.BLOCKER)
    void testDeleteUser() {
        Response deleteResponse = UserClient.deleteUser(userId, authToken);

        assertEquals(200, deleteResponse.getStatusCode());
        assertEquals("Registro excluído com sucesso", deleteResponse.jsonPath().getString("message"));

        Response getResponse = UserClient.getUser(userId, authToken);
        assertEquals(400, getResponse.getStatusCode());
        assertEquals("Usuário não encontrado", getResponse.jsonPath().getString("message"));
    }

    @Test
    @Order(6)
    @DisplayName("Rate Limiting - Validar limite de requisições")
    @Severity(SeverityLevel.MINOR)
    void testRateLimiting() {
        Response initialRequest = UserClient.getAllUsers(authToken);
        assertEquals(200, initialRequest.getStatusCode(), "A API deveria responder normalmente antes do limite");

        int rateLimit = 100;
        int successfulRequests = 0;

        for (int i = 0; i < rateLimit + 5; i++) { // +5 para garantir que ultrapasse o limite
            Response currentResponse = UserClient.getAllUsers(authToken);

            if (currentResponse.getStatusCode() == 200) {
                successfulRequests++;
            } else if (currentResponse.getStatusCode() == 429) {
                break; // Sai do loop quando atingir o limite
            }
        }

        assertTrue(successfulRequests >= rateLimit,
                "Deveria permitir pelo menos " + rateLimit + " requisições bem-sucedidas");

        Response overLimitResponse = UserClient.getAllUsers(authToken);
        assertEquals(429, overLimitResponse.getStatusCode(),
                "Deveria retornar 429 (Too Many Requests) após ultrapassar o limite");

        await().pollInterval(5, TimeUnit.SECONDS)
                .atMost(1, TimeUnit.MINUTES)
                .untilAsserted(() -> {
                    Response resetCheck = UserClient.getAllUsers(authToken);
                    assertEquals(200, resetCheck.getStatusCode(),
                            "Deveria resetar o limite após 1 minuto");
                });
    }
}