package taba.tabaServer.tabaserver.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import taba.tabaServer.tabaserver.config.AuthTokens;
import taba.tabaServer.tabaserver.config.AuthTokensGenerator;
import taba.tabaServer.tabaserver.config.infra.naver.NaverInfoResponse;
import taba.tabaServer.tabaserver.config.infra.naver.NaverUserDto;
import taba.tabaServer.tabaserver.config.oauth.OAuthInfoResponse;
import taba.tabaServer.tabaserver.domain.User;
import taba.tabaServer.tabaserver.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class NaverLoginService {

    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;

    //로그인 하기
    public AuthTokens login(NaverUserDto naverUserDto) {
        NaverInfoResponse naverInfo = createNaverInfoResponse(naverUserDto);
        /**
         * subject인 email로 사용자 정보찾기
         */
        String email = naverInfo.getResponse().getEmail();
        //사용자 중복 검증 및 회원가입
        Long userId = findOrCreateMember(naverInfo);
        return authTokensGenerator.generate(email);
    }

    //사용자 id 찾기(새로운 사용자면 회원가입)
    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }


    private NaverInfoResponse createNaverInfoResponse(NaverUserDto dto) {
        NaverInfoResponse naverInfoResponse = new NaverInfoResponse();
        NaverInfoResponse.Response response = new NaverInfoResponse.Response();
        response.setEmail(dto.getEmail());
        response.setName(dto.getName());
        response.setGender(dto.getGender());
        response.setBirthday(dto.getBirthday());
        response.setBirthyear(dto.getBirthyear());
        response.setMobile(dto.getMobile());
        naverInfoResponse.setResponse(response);
        return naverInfoResponse;
    }

    //소셜 로그인 회원가입 서비스
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
