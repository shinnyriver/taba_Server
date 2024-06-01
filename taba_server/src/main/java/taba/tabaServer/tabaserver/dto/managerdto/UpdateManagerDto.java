package taba.tabaServer.tabaserver.dto.managerdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record UpdateManagerDto(
        @JsonProperty("login_id") String id,
        @JsonProperty("password") String password
) implements Serializable {
    public static UpdateManagerDto of(
            final String id,
            final String password
    ) {
        return UpdateManagerDto.builder()
                .id(id)
                .password(password)
                .build();
    }
}
