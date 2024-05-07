package taba.tabaServer.tabaserver.config.infra.naver;


import taba.tabaServer.tabaserver.config.oauth.OAuthInfoResponse;
import taba.tabaServer.tabaserver.config.oauth.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OAuthInfoResponse {


    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        private String email;
        private String name;
        private String gender;
        private String birthday;
        private String birthyear;
        private String mobile;
    }

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

}