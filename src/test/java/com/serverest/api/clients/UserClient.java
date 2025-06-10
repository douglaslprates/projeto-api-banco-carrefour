package com.serverest.api.clients;

import com.serverest.api.models.UserDTO;
import com.serverest.api.utils.RequestHelper;
import io.restassured.response.Response;

public class UserClient {
    private static final String USERS_ENDPOINT = "/usuarios";
    private static final String USER_BY_ID_ENDPOINT = "/usuarios/{id}";


    public static Response createUser(UserDTO user, String token) {
        return RequestHelper.post(USERS_ENDPOINT, token, user);
    }

    public static Response getUser(String id, String token) {
        return RequestHelper.get(USER_BY_ID_ENDPOINT.replace("{id}", id), token);
    }

    public static Response getAllUsers(String token) {
        return RequestHelper.get(USERS_ENDPOINT, token);
    }

    public static Response updateUser(String id, UserDTO user, String token) {
        return RequestHelper.put(
                USER_BY_ID_ENDPOINT.replace("{id}", id),
                token,
                user
        );
    }

    public static Response deleteUser(String id, String token) {
        return RequestHelper.delete(
                USER_BY_ID_ENDPOINT.replace("{id}", id),
                token
        );
    }

    public static Response getUsersWithFilters(String token, java.util.Map<String, Object> queryParams) {
        return RequestHelper.getWithQueryParams(
                USERS_ENDPOINT,
                token,
                queryParams
        );
    }
}