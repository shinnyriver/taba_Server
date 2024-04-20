package taba.tabaServer.tabaserver.dto.drivingsessiondto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.DrivingStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record DrivingSessionRequestDto(
        @JsonProperty("userId") Long userId,
        @JsonProperty("drivingStatus")DrivingStatus drivingStatus
        ) implements Serializable {
            public static DrivingSessionRequestDto of(
                    final Long userId,
                    final DrivingStatus drivingStatus
            ) {
                return DrivingSessionRequestDto.builder()
                        .userId(userId)
                        .drivingStatus(drivingStatus)
                        .build();
            }
}
