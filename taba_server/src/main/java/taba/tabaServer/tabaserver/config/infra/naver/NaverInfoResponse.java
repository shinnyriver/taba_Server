package taba.tabaServer.tabaserver.config.infra.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import taba.tabaServer.tabaserver.config.oauth.OAuthInfoResponse;
import taba.tabaServer.tabaserver.config.oauth.OAuthProvider;
import taba.tabaServer.tabaserver.enums.UserActiveStatus;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OAuthInfoResponse {

    @JsonProperty("response")
    private Response response;

    @Override
    public String getEmail() {
        return response.email;
    }

    @Override
    public String getName() {
        return response.name;
    }

    @Override
    public String getGender() {
        return response.gender;
    }

    @Override
    public String getBirthday() {
        return response.birthday;
    }

    @Override
    public String getBirthyear() {
        return response.birthyear;
    }

    @Override
    public String getMobile() {
        return response.mobile;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.NAVER;
    }

    /**
     * 회원 가입시 ACTIVE 상태로 저장
     */
    @Override
    public UserActiveStatus getUserActiveStatus() {
        return UserActiveStatus.ACTIVE;
    }

    // 이 메소드를 추가하여 Response 객체를 직접 설정할 수 있게 합니다.
    public void setResponse(Response response) {
        this.response = response;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response { // 여기서 `public` 접근 제어자를 추가합니다.
        private String email;
        private String name;
        private String gender;
        private String birthday;
        private String birthyear;
        private String mobile;

        public void setEmail(String email) {
            this.email = email;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setBirthyear(String birthyear) {
            this.birthyear = birthyear;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
