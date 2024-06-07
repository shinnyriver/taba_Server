package taba.tabaServer.tabaserver.config.infra.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import taba.tabaServer.tabaserver.config.oauth.OAuthLoginParams;
import taba.tabaServer.tabaserver.config.oauth.OAuthProvider;

@Getter
@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {
    private String authorizationCode;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    /**
     * 카카오 API 요청에 필요한 authorizationCode 를 갖음.
     */
    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }
}