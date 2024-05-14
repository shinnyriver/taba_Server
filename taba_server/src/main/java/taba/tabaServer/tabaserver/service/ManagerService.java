package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import taba.tabaServer.tabaserver.component.JwtUtil;
import taba.tabaServer.tabaserver.domain.Manager;
import taba.tabaServer.tabaserver.dto.managerdto.CreateManagerDto;
import taba.tabaServer.tabaserver.dto.managerdto.ManagerLoginDto;
import taba.tabaServer.tabaserver.dto.managerdto.ResponseManagerDto;
import taba.tabaServer.tabaserver.repository.ManagerRepository;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseManagerDto createManager(CreateManagerDto createManagerDto){
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

    public String login(ManagerLoginDto managerLoginDto){
        Manager manager = managerRepository.findByLoginId(managerLoginDto.loginId());
        if (manager != null && passwordEncoder.matches(managerLoginDto.password(), manager.getPassword())) {
            return jwtUtil.generateToken(manager.getLoginId());
        }
        return null;
    }
}
