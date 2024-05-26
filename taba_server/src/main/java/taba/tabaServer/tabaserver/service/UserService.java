package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taba.tabaServer.tabaserver.domain.User;
import taba.tabaServer.tabaserver.dto.userdto.CreateUserDto;
import taba.tabaServer.tabaserver.dto.userdto.UpdateUserDto;
import taba.tabaServer.tabaserver.dto.userdto.UserResponseDto;
import taba.tabaServer.tabaserver.dto.userdto.UserStatisticsDto;
import taba.tabaServer.tabaserver.exception.CommonException;
import taba.tabaServer.tabaserver.exception.ErrorCode;
import taba.tabaServer.tabaserver.repository.UserRepository;
import taba.tabaServer.tabaserver.security.config.SecurityConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        return UserResponseDto.of(
                user.getId(),
                user.getEmail(),
                user.getGender(),
                user.getBirthyear(),
                user.getBirthday()
        );
    }

    @Transactional
    public Boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserResponseDto.of(
                        user.getId(),
                        user.getEmail(),
                        user.getGender(),
                        user.getBirthyear(),
                        user.getBirthday()
                        )
                ).collect(Collectors.toList());
    }

    @Transactional
    public UserStatisticsDto getUserStatisticsBetweenDates(LocalDateTime start, LocalDateTime end){
        Long newUsers = userRepository.countByCreatedAtBetween(start, end);
        Long withdrawUsers = userRepository.countByWithdrawAtBetween(start, end);
        return UserStatisticsDto.of(
                newUsers,
                withdrawUsers
        );
    }
}
