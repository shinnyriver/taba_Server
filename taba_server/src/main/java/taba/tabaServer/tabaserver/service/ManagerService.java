package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taba.tabaServer.tabaserver.component.JwtTokenService;
import taba.tabaServer.tabaserver.domain.Manager;
import taba.tabaServer.tabaserver.dto.managerdto.*;
import taba.tabaServer.tabaserver.exception.CommonException;
import taba.tabaServer.tabaserver.exception.ErrorCode;
import taba.tabaServer.tabaserver.repository.ManagerRepository;


@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final JwtTokenService jwtTokenService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ResponseManagerDto createManager(CreateManagerDto createManagerDto) {
        String encodedPassword = passwordEncoder.encode(createManagerDto.password());
        Manager save = managerRepository.save(Manager.builder()
                .loginId(createManagerDto.loginId())
                .password(encodedPassword)
                .name(createManagerDto.name())
                .managerType(createManagerDto.managerType())
                .build()
        );

        return ResponseManagerDto.builder()
                .loginId(save.getLoginId())
                .name(save.getName())
                .managerType(save.getManagerType())
                .build();
    }

    public JwtResponseDto login(ManagerLoginDto managerLoginDto) {
        Manager manager = managerRepository.findByLoginId(managerLoginDto.loginId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MANAGER));
        if (manager != null && passwordEncoder.matches(managerLoginDto.password(), manager.getPassword())) {
            String jwt = jwtTokenService.generateToken(manager.getLoginId());
            return JwtResponseDto.builder()
                    .jwt(jwt)
                    .name(manager.getName())
                    .build();
        }

        return JwtResponseDto.builder()
                .jwt(null)
                .name(null)
                .build();
    }

    @Transactional
    public Boolean updatePassword(UpdateManagerDto updateManagerDto) {
        Manager manager = managerRepository.findByLoginId(updateManagerDto.id())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MANAGER));
        if (passwordEncoder.matches(updateManagerDto.pastPassword(), manager.getPassword())) {
            String encodedPassword = passwordEncoder.encode(updateManagerDto.newPassword());
            manager.updatePassword(encodedPassword);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
