package taba.tabaServer.tabaserver.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.GenderEnum;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
public record CreateUserDto(
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("gender") GenderEnum gender,
        @JsonProperty("userBirth") LocalDate userBirth
) implements Serializable {
    public static CreateUserDto of(
            String email,
            String password,
            GenderEnum gender,
            LocalDate userBirth
        ) {
        return CreateUserDto.builder()
                .email(email)
                .password(password)
                .gender(gender)
                .userBirth(userBirth)
                .build();
    }
}
