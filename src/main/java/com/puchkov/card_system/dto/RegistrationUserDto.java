package com.puchkov.card_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationUserDto {

    @NotBlank(message = "Имя обязательно")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    @Schema(description = "Имя и Фамилия пользователя", example = "Ivan Ivanov")
    private String nameSurname;

    @NotNull
    @Size(min = 3, max = 50, message = "Пароль должен быть от 3 до 50 символов")
    @Schema(description = "Пароль", example = "password111")
    private String password;

    @NotNull
    @Size(min = 3, max = 50, message = "Пароль должен быть от 3 до 50 символов")
    @Schema(description = "Подтверждение пароля", example = "password111")
    private String confirmPassword;

    @Email(message = "Некорректный email")
    @Schema(description = "Пароль пользователя", example = "ivanov@mail.com")
    private String email;

}
