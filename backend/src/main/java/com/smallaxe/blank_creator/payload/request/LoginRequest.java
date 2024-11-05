package com.smallaxe.blank_creator.payload.request;

//import jakarta.validation.constraints.NotBlank;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Введите имя пользователя")
    private String username;

    @NotBlank(message = "Введите пароль")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
