package taba.tabaServer.tabaserver.dto.managerdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.ManagerType;

import java.io.Serializable;

@Builder
public record ResponseManagerDto(
        @JsonProperty("login_id") String loginId,
        @JsonProperty("name") String name,
        @JsonProperty("manager_type") ManagerType managerType
) implements Serializable {
    public static ResponseManagerDto of(
            final String loginId,
            final String name,
            final ManagerType managerType
    ) {
        return ResponseManagerDto.builder()
                .loginId(loginId)
                .name(name)
                .managerType(managerType)
                .build();
    }
}
