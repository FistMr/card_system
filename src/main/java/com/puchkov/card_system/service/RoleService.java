package com.puchkov.card_system.service;

import com.puchkov.card_system.entity.Role;
import com.puchkov.card_system.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole(String nameSurname) {
        return roleRepository.findByName(nameSurname).get();
    }
}
