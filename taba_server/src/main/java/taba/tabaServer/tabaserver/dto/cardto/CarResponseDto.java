package taba.tabaServer.tabaserver.dto.cardto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.CarSize;

import java.io.Serializable;

@Builder
public record CarResponseDto(
        @JsonProperty("carName") String carName,
        @JsonProperty("carSize") CarSize carSize,
        @JsonProperty("totalDistance") int totalDistance,
        @JsonProperty("carNumber") String carNumber,
        @JsonProperty("photo") byte[] photo,
        @JsonProperty("userId") Long userId
        ) implements Serializable {
            public static CarResponseDto of(
                    final String carName,
                    final CarSize carSize,
                    final int totalDistance,
                    final String carNumber,
                    final byte[] photo,
                    final Long userid
            ) {
                return CarResponseDto.builder()
                        .carName(carName)
                        .carSize(carSize)
                        .totalDistance(totalDistance)
                        .carNumber(carNumber)
                        .photo(photo)
                        .userId(userid)
                        .build();
            }
}
