package taba.tabaServer.tabaserver.config.infra.naver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import taba.tabaServer.tabaserver.config.oauth.OAuthLoginParams;
import taba.tabaServer.tabaserver.config.oauth.OAuthProvider;

@Getter
@NoArgsConstructor
public class NaverLoginParams implements OAuthLoginParams {
    private String authorizationCode;
    private String state;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }

    /**
     * 네이버 API 요청에 필요한 authorizationCode 를 갖음.
     */
    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("state", state);
        return body;
    }
}