package taba.tabaServer.tabaserver.dto.cardto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record CarDrivingScoreUpdateRequestDto(
        @JsonProperty("driving_score") int drivingScore
) implements Serializable {
    public static CarDrivingScoreUpdateRequestDto of(
            final int drivingScore
    ) {
        return CarDrivingScoreUpdateRequestDto.builder()
                .drivingScore(drivingScore)
                .build();
    }
}