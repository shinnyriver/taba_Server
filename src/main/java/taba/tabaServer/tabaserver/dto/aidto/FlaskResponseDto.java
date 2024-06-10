package taba.tabaServer.tabaserver.dto.aidto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record FlaskResponseDto(
        @JsonProperty("message") String message,
        @JsonProperty("result") String result
) implements Serializable {
    public static FlaskResponseDto of(
            final String message,
            final String result
    ) {
        return FlaskResponseDto.builder()
                .message(message)
                .result(result)
                .build();
    }
}
