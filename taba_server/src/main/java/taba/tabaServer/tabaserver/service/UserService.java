package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taba.tabaServer.tabaserver.domain.User;
import taba.tabaServer.tabaserver.dto.userdto.CreateUserDto;
import taba.tabaServer.tabaserver.dto.userdto.UpdateUserDto;
import taba.tabaServer.tabaserver.dto.userdto.UserResponseDto;
import taba.tabaServer.tabaserver.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDto createUser(CreateUserDto dto) {
        User newUser = User.builder()
                .email(dto.email())
                .password(dto.password())
                .gender(dto.gender())
                .userBirth(dto.userBirth())
                .build();
        User savedUser = userRepository.save(newUser);
        return UserResponseDto.of(savedUser.getId(), savedUser.getEmail(),
                savedUser.getGender(), savedUser.getUserBirth());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserResponseDto.of(user.getId(), user.getEmail(), user.getGender(), user.getUserBirth());
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UpdateUserDto dto) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.updateUser(dto.email(), dto.password(), dto.gender(), dto.userBirth());
        userRepository.save(existingUser);
        return UserResponseDto.of(existingUser.getId(), existingUser.getEmail(),
                existingUser.getGender(), existingUser.getUserBirth());
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserResponseDto.of(user.getId(), user.getEmail(),
                        user.getGender(), user.getUserBirth()))
                .collect(Collectors.toList());
    }
}
