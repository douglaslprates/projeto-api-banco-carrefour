package com.serverest.api.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthClient {
    private static final String BASE_URL = "https://serverest.dev";

    public static String getAuthToken(String email, String password) {
        Response response = given()
                .contentType("application/json")
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password))
                .when()
                .post(BASE_URL + "/login");

        return response.jsonPath().getString("authorization");
    }
}