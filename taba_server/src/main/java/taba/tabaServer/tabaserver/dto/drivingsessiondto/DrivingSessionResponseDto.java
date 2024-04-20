package taba.tabaServer.tabaserver.dto.drivingsessiondto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.DrivingStatus;
import taba.tabaServer.tabaserver.enums.ErrorStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record DrivingSessionResponseDto(
        @JsonProperty("userId") Long userId,
        @JsonProperty("startTime") LocalDateTime startTime,
        @JsonProperty("endTime") LocalDateTime endTime,
        @JsonProperty("errorTime") LocalDateTime errorTime,
        @JsonProperty("drivingStatus") DrivingStatus drivingStatus,
        @JsonProperty("errorStatus")ErrorStatus errorStatus
        ) implements Serializable {
            public static DrivingSessionResponseDto of(
                    final Long userId,
                    final LocalDateTime startTime,
                    final LocalDateTime endTime,
                    final LocalDateTime errorTime,
                    final DrivingStatus drivingStatus,
                    final ErrorStatus errorStatus
            ) {
                return DrivingSessionResponseDto.builder()
                        .userId(userId)
                        .startTime(startTime)
                        .endTime(endTime)
                        .errorTime(errorTime)
                        .drivingStatus(drivingStatus)
                        .errorStatus(errorStatus)
                        .build();
            }
}
