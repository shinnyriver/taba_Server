package taba.tabaServer.tabaserver.dto.cardto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
public record CarDrivingScoreUpdateRequestDto(
        @JsonProperty("driving_score") int drivingScore
) {}