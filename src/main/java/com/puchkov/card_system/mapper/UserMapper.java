package com.puchkov.card_system.mapper;

import com.puchkov.card_system.dto.UserDto;
import com.puchkov.card_system.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserDto> {

    @Override
    public UserDto fromEntity(User object) {
        return UserDto.builder()
                .id(object.getId())
                .nameSurname(object.getNameSurname())
                .email(object.getEmail())
                .build();
    }
}
