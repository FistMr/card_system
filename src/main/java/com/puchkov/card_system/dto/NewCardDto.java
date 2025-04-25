package com.puchkov.card_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewCardDto {
    @NotBlank(message = "Имя и Фамилия владельца обязательны")
    @Size(min=3, max=150)
    @Schema(description = "Имя и Фамилмя пользователя", example = "Ivan Ivanov")
    private String ownerNameSurname;
}
