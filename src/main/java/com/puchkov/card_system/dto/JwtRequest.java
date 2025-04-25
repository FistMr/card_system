package com.puchkov.card_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class JwtRequest {
    @NotNull
    @Schema(description = "Имя и Фамилия пользователя", example = "Ivan Ivanov")
    private String nameSurname;
    @NotNull
    @Schema(description = "Пароль", example = "password111")
    private String password;
}
