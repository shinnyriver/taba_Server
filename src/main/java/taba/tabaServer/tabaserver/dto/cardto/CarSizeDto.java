package taba.tabaServer.tabaserver.dto.cardto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record CarSizeDto(
        @JsonProperty("small") Long small,
        @JsonProperty("medium") Long medium,
        @JsonProperty("large") Long large
) implements Serializable {
    public static CarSizeDto of(
            final Long small,
            final Long medium,
            final Long large
    ) {
        return CarSizeDto.builder()
                .small(small)
                .medium(medium)
                .large(large)
                .build();
    }
}
