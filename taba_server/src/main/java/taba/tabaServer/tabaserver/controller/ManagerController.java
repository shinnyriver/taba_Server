package taba.tabaServer.tabaserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taba.tabaServer.tabaserver.dto.global.AuthenticationResponse;
import taba.tabaServer.tabaserver.dto.global.ResponseDto;
import taba.tabaServer.tabaserver.dto.managerdto.CreateManagerDto;
import taba.tabaServer.tabaserver.dto.managerdto.ManagerLoginDto;
import taba.tabaServer.tabaserver.dto.managerdto.UpdateManagerDto;
import taba.tabaServer.tabaserver.exception.CommonException;
import taba.tabaServer.tabaserver.exception.ErrorCode;
import taba.tabaServer.tabaserver.service.ManagerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/managers")
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("/register")
    public ResponseDto<?> registerManager(@RequestBody CreateManagerDto createManagerDto) {
        return ResponseDto.ok(managerService.createManager(createManagerDto));
    }

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody ManagerLoginDto managerLoginDto) {
        String token = managerService.login(managerLoginDto).jwt();
        if (token != null) {
            AuthenticationResponse authenticationResponse = new AuthenticationResponse(token);
            return ResponseDto.ok(managerService.login(managerLoginDto));
        } else {
            return ResponseDto.fail(new CommonException(ErrorCode.LOGIN_FAILURE));
        }
    }

    @PostMapping("/update")
    public ResponseDto<?> updatePassword(@RequestBody UpdateManagerDto updateManagerDto) {
        return ResponseDto.ok(managerService.updatePassword(updateManagerDto));
    }
}
