package taba.tabaServer.tabaserver.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.GenderEnum;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
public record UserResponseDto(
        @JsonProperty("id") Long id,
        @JsonProperty("email") String email,
        @JsonProperty("gender") GenderEnum gender,
        @JsonProperty("userBirth") LocalDate userBirth
) implements Serializable {
    public static UserResponseDto of(
            Long id,
            String email,
            GenderEnum gender,
            LocalDate userBirth
    ) {
        return UserResponseDto.builder()
                .id(id)
                .email(email)
                .gender(gender)
                .userBirth(userBirth)
                .build();
    }
}
