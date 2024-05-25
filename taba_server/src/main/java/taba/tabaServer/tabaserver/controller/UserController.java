package taba.tabaServer.tabaserver.controller;

import taba.tabaServer.tabaserver.config.AuthTokensGenerator;
import taba.tabaServer.tabaserver.domain.User;
import taba.tabaServer.tabaserver.dto.global.ResponseDto;
import taba.tabaServer.tabaserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;

    //모든 사용자 찾기
    @GetMapping
    public ResponseDto<?> findAll() {
        return ResponseDto.ok(userRepository.findAll());
    }

    //엑세스 토큰 기반 조회
    @GetMapping("/token/{accessToken}")
    public ResponseDto<?> findByAccessToken(@PathVariable String accessToken) {
        Long memberId = authTokensGenerator.extractMemberId(accessToken);
        return ResponseDto.ok(userRepository.findById(memberId));
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

}