package taba.tabaServer.tabaserver.dto.managerdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record UpdateManagerDto(
        @JsonProperty("login_id") String id,
        @JsonProperty("past_password") String pastPassword,
        @JsonProperty("new_password") String newPassword
) implements Serializable {
    public static UpdateManagerDto of(
            final String id,
            final String pastPassword,
            final String newPassword
    ) {
        return UpdateManagerDto.builder()
                .id(id)
                .pastPassword(pastPassword)
                .newPassword(newPassword)
                .build();
    }
}
