package com.serverest.api.utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RequestHelper {
    private static final String BASE_URL = "https://serverest.dev";
    private static RequestSpecification requestSpec;

    static {
        // Configuração inicial comum a todas as requisições
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .addHeader("accept", "application/json")
                .build();
    }

    /**
     * Executa uma requisição GET com autenticação
     */
    public static Response get(String endpoint, String token) {
        return RestAssured.given()
                .spec(requestSpec)
                .header("Authorization", token)
                .when()
                .get(endpoint);
    }

    /**
     * Executa uma requisição GET com parâmetros de query
     */
    public static Response getWithQueryParams(String endpoint, String token, Map<String, Object> queryParams) {
        return RestAssured.given()
                .spec(requestSpec)
                .header("Authorization", token)
                .queryParams(queryParams)
                .when()
                .get(endpoint);
    }

    /**
     * Executa uma requisição POST com autenticação
     */
    public static Response post(String endpoint, String token, Object body) {
        return RestAssured.given()
                .spec(requestSpec)
                .header("Authorization", token)
                .body(body)
                .when()
                .post(endpoint);
    }

    /**
     * Executa uma requisição PUT com autenticação
     */
    public static Response put(String endpoint, String token, Object body) {
        return RestAssured.given()
                .spec(requestSpec)
                .header("Authorization", token)
                .body(body)
                .when()
                .put(endpoint);
    }

    /**
     * Executa uma requisição DELETE com autenticação
     */
    public static Response delete(String endpoint, String token) {
        return RestAssured.given()
                .spec(requestSpec)
                .header("Authorization", token)
                .when()
                .delete(endpoint);
    }

    /**
     * Extrai valor de um campo JSON da resposta
     */
    public static String extractField(Response response, String field) {
        return response.jsonPath().getString(field);
    }

    /**
     * Configura o timeout para todas as requisições
     */
    public static void setGlobalTimeout(int milliseconds) {
        RestAssured.config = RestAssured.config()
                .httpClient(RestAssured.config().getHttpClientConfig()
                        .setParam("http.connection.timeout", milliseconds)
                        .setParam("http.socket.timeout", milliseconds));
    }
}