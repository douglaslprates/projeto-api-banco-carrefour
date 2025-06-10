package com.serverest.api.utils;

import com.github.javafaker.Faker;
import com.serverest.api.models.UserDTO;

public class TestDataGenerator {
    private static final Faker faker = new Faker();

    public static UserDTO generateRandomUser() {
        return UserDTO.builder()
                .nome(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .administrador("true")
                .build();
    }
}