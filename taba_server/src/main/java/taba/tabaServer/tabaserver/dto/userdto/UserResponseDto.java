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
        @JsonProperty("gender") String gender,
        @JsonProperty("birthyear") String birthyear,
        @JsonProperty("birthday") String birthday
) implements Serializable {
    public static UserResponseDto of(
            final Long id,
            final String email,
            final String gender,
            final String birthyear,
            final String birthday
    ) {
        return UserResponseDto.builder()
                .id(id)
                .email(email)
                .gender(gender)
                .birthyear(birthyear)
                .birthday(birthday)
                .build();
    }
}
