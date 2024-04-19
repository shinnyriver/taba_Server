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

    @Transactional
    public UserResponseDto createUser(CreateUserDto createUserDto) {
        User newUser = User.builder()
                .email(createUserDto.email())
                .password(createUserDto.password())
                .gender(createUserDto.gender())
                .userBirth(createUserDto.userBirth())
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
    public UserResponseDto updateUser(Long id, UpdateUserDto updateUserDto) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.updateUser(updateUserDto.email(), updateUserDto.password(), updateUserDto.gender(), updateUserDto.userBirth());
        userRepository.save(existingUser);
        return UserResponseDto.of(existingUser.getId(), existingUser.getEmail(),
                existingUser.getGender(), existingUser.getUserBirth());
    }

    @Transactional
    public Boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserResponseDto.of(user.getId(), user.getEmail(),
                        user.getGender(), user.getUserBirth()))
                .collect(Collectors.toList());
    }

}
