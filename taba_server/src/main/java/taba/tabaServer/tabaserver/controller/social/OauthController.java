package taba.tabaServer.tabaserver.controller.social;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taba.tabaServer.tabaserver.config.AuthTokens;
import taba.tabaServer.tabaserver.config.infra.kakao.KakaoLoginParams;
import taba.tabaServer.tabaserver.config.infra.naver.NaverUserDto;
import taba.tabaServer.tabaserver.security.service.NaverLoginService;
import taba.tabaServer.tabaserver.security.service.OAuthLoginService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OauthController {
    //oauth 로그인 서비스
    private final OAuthLoginService oAuthLoginService;
    private final NaverLoginService naverLoginService;

    //카카오 로그인
    @PostMapping("/oauth/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    //네이버 로그인
    @PostMapping("/oauth/naver")
    public ResponseEntity<AuthTokens> loginNaver(@RequestBody NaverUserDto naverUserDto) {
        return ResponseEntity.ok(naverLoginService.login(naverUserDto));
    }

}