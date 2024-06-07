package taba.tabaServer.tabaserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import taba.tabaServer.tabaserver.config.AuthTokensGenerator;
import taba.tabaServer.tabaserver.dto.global.ResponseDto;
import taba.tabaServer.tabaserver.repository.UserRepository;
import taba.tabaServer.tabaserver.service.UserService;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final UserService userService;

    //모든 사용자 찾기
    @GetMapping
    public ResponseDto<?> findAll() {
        return ResponseDto.ok(userRepository.findAll());
    }

    //엑세스 토큰 기반 조회(토큰의 subject인 email로 조사)
    @GetMapping("/token/{accessToken}")
    public ResponseDto<?> findByAccessToken(@PathVariable String accessToken) {
        String email = authTokensGenerator.extractUserEmail(accessToken);
        return ResponseDto.ok(userRepository.findByEmail(email));
    }

    //사용자 id로 조회
    @GetMapping("/{id}")
    public ResponseDto<?> findById(@PathVariable Long id) {
        return ResponseDto.ok(userRepository.findById(id));
    }

    //사용자 이름으로 조회
    @GetMapping("/name/{name}")
    public ResponseDto<?> findByName(@PathVariable String name) {
        return ResponseDto.ok(userRepository.findByName(name));
    }

    //유저 통계 usage : /api/users/statistics?startDate=2024-01-01T00:00:00&endDate=2024-01-31T23:59:59
    @GetMapping("/statistics")
    public ResponseDto<?> getUserStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return ResponseDto.ok(userService.getUserStatisticsBetweenDates(start, end));
    }

}