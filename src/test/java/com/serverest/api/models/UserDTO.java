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

    // Construtor vazio
    public UserDTO() {}

    // Construtor com todos os campos
    public UserDTO(String nome, String email, String password, String administrador) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
    }

    // Método builder estático
    public static Builder builder() {
        return new Builder();
    }

    // Implementação do Builder
    public static final class Builder {
        private String nome;
        private String email;
        private String password;
        private String administrador;

        private Builder() {}

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder administrador(String administrador) {
            this.administrador = administrador;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(nome, email, password, administrador);
        }
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }
}