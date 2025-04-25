package com.puchkov.card_system.service;

import com.puchkov.card_system.dto.RegistrationUserDto;
import com.puchkov.card_system.dto.UserDto;
import com.puchkov.card_system.entity.User;
import com.puchkov.card_system.mapper.UserMapper;
import com.puchkov.card_system.repository.UserRepository;
import com.puchkov.card_system.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Пользователь не найден: " + id));
        userRepository.delete(user);
    }

    public ResponseEntity<?> update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(
                () -> new IllegalArgumentException("Пользователь не найден: " + userDto.getId()));
        user.setNameSurname(userDto.getNameSurname());
        user.setEmail(userDto.getEmail());
        user = userRepository.save(user);
        return ResponseEntity.ok(userMapper.fromEntity(user));
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::fromEntity)
                .toList();
    }

    public Optional<User> findByNameSurname(String nameSurname) {
        return userRepository.findByNameSurname(nameSurname);
    }

    @Override
    @Transactional
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByNameSurname(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        return new CustomUser(
                user.getNameSurname(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()),
                user.getId(),
                user.getEmail()
        );
    }

    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setNameSurname(registrationUserDto.getNameSurname());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleService.getUserRole("ROLE_USER")));
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return  userRepository.findById(id);
    }
}
