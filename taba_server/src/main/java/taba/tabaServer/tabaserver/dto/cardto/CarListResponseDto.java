package taba.tabaServer.tabaserver.dto.cardto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.CarSize;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record CarListResponseDto(
        @JsonProperty("car_id") Long carId,
        @JsonProperty("car_size") CarSize carSize,
        @JsonProperty("car_number") String carNumber,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("withdraw_at") LocalDateTime withdrawAt
) implements Serializable {
    public static CarListResponseDto of(
            final Long carId,
            final CarSize carSize,
            final String carNumber,
            final LocalDateTime createdAt,
            final LocalDateTime withdrawAt
    ) {
        return CarListResponseDto.builder()
                .carId(carId)
                .carSize(carSize)
                .carNumber(carNumber)
                .createdAt(createdAt)
                .withdrawAt(withdrawAt)
                .build();
    }
}
