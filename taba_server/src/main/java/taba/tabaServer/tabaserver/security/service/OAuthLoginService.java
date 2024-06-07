package taba.tabaServer.tabaserver.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import taba.tabaServer.tabaserver.config.AuthTokens;
import taba.tabaServer.tabaserver.config.AuthTokensGenerator;
import taba.tabaServer.tabaserver.config.oauth.OAuthInfoResponse;
import taba.tabaServer.tabaserver.config.oauth.OAuthLoginParams;
import taba.tabaServer.tabaserver.config.oauth.RequestOAuthInfoService;
import taba.tabaServer.tabaserver.domain.User;
import taba.tabaServer.tabaserver.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    /**
     * 1. 카카오/네이버와 같은 OAuth 플랫폼에 인증 후 프로필 정보 가져오기
     * 2. email 정보로 사용자 확인 (없으면 새로 가입처리)
     * 3. Access Token 생성 후 내려주기
     */
    //로그인 하기
    // 로그인 하기
    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        /**
         * subject인 email로 사용자 정보찾기
         */
        String email = oAuthInfoResponse.getEmail();
        Long userId = findOrCreateMember(oAuthInfoResponse);
        //토큰 생성
        AuthTokens tokens = authTokensGenerator.generate(email);
        // 로그 추가
        System.out.println("Generated Tokens: " + tokens);

        return tokens;
    }

    //사용자 id 찾기
    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    //소셜 로그인 회원가입 서비스(carInsurance => 앱에서 입력 ,createdAt => 자동생성)
    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .name(oAuthInfoResponse.getName())
                .oauthProvider(oAuthInfoResponse.getOAuthProvider())
                .gender(oAuthInfoResponse.getGender())
                .birthyear(oAuthInfoResponse.getBirthyear())
                .birthday(oAuthInfoResponse.getBirthday())
                .mobile(oAuthInfoResponse.getMobile())
                .userActiveStatus(oAuthInfoResponse.getUserActiveStatus())
                .build();

        return userRepository.save(user).getId();
    }
}