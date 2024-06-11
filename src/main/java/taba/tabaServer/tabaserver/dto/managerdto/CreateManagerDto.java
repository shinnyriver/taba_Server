package taba.tabaServer.tabaserver.dto.managerdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.ManagerType;

import java.io.Serializable;

@Builder
public record CreateManagerDto(
        @JsonProperty("login_id") String loginId,
        @JsonProperty("password") String password,
        @JsonProperty("name") String name,
        @JsonProperty("manager_type") ManagerType managerType
) implements Serializable {
    public static CreateManagerDto of(
            final String loginId,
            final String password,
            final String name,
            final ManagerType managerType
    ) {
        return CreateManagerDto.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .managerType(managerType)
                .build();
    }
}
