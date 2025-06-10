package com.serverest.api.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String nome;
    private String email;
    private String password;
    private String administrador;
}