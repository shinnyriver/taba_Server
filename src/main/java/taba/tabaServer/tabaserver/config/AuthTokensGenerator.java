package taba.tabaServer.tabaserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import taba.tabaServer.tabaserver.component.JwtTokenService;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokensGenerator {
    /**
     * AuthTokens 을 발급해주는 클래스
     */
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 10;            // 600분(10시간)
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final JwtTokenService jwtTokenService;

    //user email(사용자 식별값) 을 받아 Access Token 을 생성

    /**
     * user jwt subject: email(oauth2에서 사용자 식별자인 email로 검증)
     */
    public AuthTokens generate(String email) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = email; //이메일을 subject로 사용
        String accessToken = jwtTokenService.generate(subject, accessTokenExpiredAt);
        String refreshToken = jwtTokenService.generate(subject, refreshTokenExpiredAt);

        // 로그 추가
        System.out.println("Generated Access Token: " + accessToken);
        System.out.println("Generated Refresh Token: " + refreshToken);

        return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    /**
     * AccessToken 에서 userEmail (사용자 식별값) 추출
     */
    public String extractUserEmail(String accessToken) {
        return jwtTokenService.extractUserEmail(accessToken);
    }
}