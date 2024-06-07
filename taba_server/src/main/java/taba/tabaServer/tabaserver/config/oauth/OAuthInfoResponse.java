package taba.tabaServer.tabaserver.config.oauth;

import taba.tabaServer.tabaserver.enums.UserActiveStatus;

public interface OAuthInfoResponse {

    /**
     * Access Token 으로 요청한 외부 API 프로필 응답값을 우리 서비스의 Model 로 변환시키기 위한 인터페이스
     * 카카오나 네이버의 email,nickname,gender,birthday,birthyear,mobile 정보를 필요로 하기 때문에 Getter 메서드를 추가
     */

    String getEmail();

    String getName();

    String getGender();

    String getBirthday();

    String getBirthyear();

    String getMobile();

    OAuthProvider getOAuthProvider();

    UserActiveStatus getUserActiveStatus();
}