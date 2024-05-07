package taba.tabaServer.tabaserver.config;

import taba.tabaServer.config.infra.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

    private final JwtTokenProvider jwtTokenProvider;

    //memberId (사용자 식별값) 을 받아 Access Token 을 생성
    public AuthTokens generate(Long memberId) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = memberId.toString();
        String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiredAt);
        String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredAt);

        return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }
    //Access Token 에서 memberId (사용자 식별값) 추출
    public Long extractMemberId(String accessToken) {
        return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
    }
}