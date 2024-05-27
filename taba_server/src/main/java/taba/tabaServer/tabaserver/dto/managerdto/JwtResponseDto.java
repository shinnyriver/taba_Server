package taba.tabaServer.tabaserver.dto.managerdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record JwtResponseDto(
        @JsonProperty("jwt") String jwt,
        @JsonProperty("name") String name
) implements Serializable {
    public static JwtResponseDto of(
            final String jwt,
            final String name
    ) {
        return JwtResponseDto.builder()
                .jwt(jwt)
                .name(name)
                .build();
    }
}
