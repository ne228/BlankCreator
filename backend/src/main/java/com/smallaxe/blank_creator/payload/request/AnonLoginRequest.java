package com.smallaxe.blank_creator.payload.request;

import javax.validation.constraints.NotBlank;

public class AnonLoginRequest {
    @NotBlank(message = "Введите имя пользователя")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
