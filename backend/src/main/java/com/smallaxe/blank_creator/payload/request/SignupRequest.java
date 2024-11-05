package com.smallaxe.blank_creator.payload.request;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class SignupRequest {
    @NotBlank(message = "Введите имя пользователя")
    @Size(min = 3, max = 20, message = "Длина имени пользователя должна быть от 3 до 20 символов")
    @JsonProperty
    private String username;

    @NotBlank(message = "Введите email")
    @Size(max = 50)
    @Email(message = "Email не валиден")
    @JsonProperty
    private String email;

    private Set<String> role;

    @NotBlank(message = "Введите пароль")
    @Size(min = 6, max = 40, message = "Длина пароля должна быть больше 6")
    @JsonProperty
    private String password;

//    @NotBlank(message = "Повторите пароль")
    @Size(min = 6, max = 40, message = "Длина пароля должна быть больше 6")
    @JsonProperty
    private String retypePassword;


    private boolean guest;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(HashSet<String> role) {
        this.role = role;
    }


    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }
}
