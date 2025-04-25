package com.puchkov.card_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    @NotNull
    @Schema(description = "id пользователя",example = "1")
    private Long id;
    @NotBlank(message = "Имя обязательно")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    @Schema(description = "Имя и Фамилия пользователя",example = "Ivan Ivanov")
    private String nameSurname;
    @Email
    @Schema(description = "Почта пользователя",example = "ivanov@mail.com")
    private String email;
}
