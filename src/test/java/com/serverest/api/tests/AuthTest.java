package com.serverest.api.tests;

import com.serverest.api.clients.AuthClient;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Auth API")
public class AuthTest {
    @Test
    @DisplayName("Login com credenciais válidas")
    @Severity(SeverityLevel.BLOCKER)
    void testValidLogin() {
        String token = AuthClient.getAuthToken("fulano@qa.com", "teste");
        assertNotNull(token);
    }
}