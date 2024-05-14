package taba.tabaServer.tabaserver.dto.managerdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record ManagerLoginDto(
        @JsonProperty("login_id") String loginId,
        @JsonProperty("password") String password
) implements Serializable {
    public static ManagerLoginDto of(
            final String loginId,
            final String password
    ) {
        return ManagerLoginDto.builder()
                .loginId(loginId)
                .password(password)
                .build();
    }
}
