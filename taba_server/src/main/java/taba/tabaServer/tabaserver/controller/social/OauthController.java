package taba.tabaServer.tabaserver.controller.social;

import org.springframework.web.bind.annotation.*;
import taba.tabaServer.tabaserver.config.infra.kakao.KakaoLoginParams;
import taba.tabaServer.tabaserver.config.infra.naver.NaverLoginParams;
import taba.tabaServer.tabaserver.dto.global.ResponseDto;
import taba.tabaServer.tabaserver.security.service.OAuthLoginService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthController {
    //oauth 로그인 서비스
    private final OAuthLoginService oAuthLoginService;

    //카카오 로그인
    @PostMapping("/kakao")
    public ResponseDto<?> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseDto.ok(oAuthLoginService.login(params));
    }

    //네이버 로그인
    @PostMapping("/naver")
    public ResponseDto<?> loginNaver(@RequestBody NaverLoginParams params) {
        return ResponseDto.ok(oAuthLoginService.login(params));
    }
}