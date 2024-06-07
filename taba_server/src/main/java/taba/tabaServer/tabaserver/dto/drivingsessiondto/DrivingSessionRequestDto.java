package taba.tabaServer.tabaserver.dto.drivingsessiondto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.DrivingStatus;

import java.io.Serializable;

@Builder
public record DrivingSessionRequestDto(
        @JsonProperty("user_id") Long userId,
        @JsonProperty("car_id") Long carId,
        @JsonProperty("driving_status") DrivingStatus drivingStatus
) implements Serializable {
    public static DrivingSessionRequestDto of(
            final Long userId,
            final Long carId,
            final DrivingStatus drivingStatus
    ) {
        return DrivingSessionRequestDto.builder()
                .userId(userId)
                .carId(carId)
                .drivingStatus(drivingStatus)
                .build();
    }
}
