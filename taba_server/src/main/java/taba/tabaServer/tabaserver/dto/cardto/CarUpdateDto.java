package taba.tabaServer.tabaserver.dto.cardto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.CarSize;

import java.io.Serializable;

@Builder
public record CarUpdateDto(
        @JsonProperty("carName") String carName,
        @JsonProperty("carSize") CarSize carSize,
        @JsonProperty("totalDistance") int totalDistance,
        @JsonProperty("carNumber") String carNumber
        ) implements Serializable {
            public static CarUpdateDto of(
                    final String carName,
                    final CarSize carSize,
                    final int totalDistance,
                    final String carNumber
            ) {
                return CarUpdateDto.builder()
                        .carName(carName)
                        .carSize(carSize)
                        .totalDistance(totalDistance)
                        .carNumber(carNumber)
                        .build();
            }
}
