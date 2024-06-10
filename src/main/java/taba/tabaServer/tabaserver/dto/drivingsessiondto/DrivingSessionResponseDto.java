package taba.tabaServer.tabaserver.dto.drivingsessiondto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.DrivingStatus;
import taba.tabaServer.tabaserver.enums.ErrorStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record DrivingSessionResponseDto(
        @JsonProperty("driving_session_id") Long drivingSessionId,
        @JsonProperty("user_id") Long userId,
        @JsonProperty("car_id") Long carId,
        @JsonProperty("start_date") LocalDate startDate,
        @JsonProperty("start_time") LocalTime startTime,
        @JsonProperty("end_date") LocalDate endDate,
        @JsonProperty("end_time") LocalTime endTime,
        @JsonProperty("error_time") LocalDateTime errorTime,
        @JsonProperty("solve_time") LocalDateTime solveTime,
        @JsonProperty("driving_status") DrivingStatus drivingStatus,
        @JsonProperty("error_latitude") String latitude,
        @JsonProperty("error_longitude") String longitude,
        @JsonProperty("error_status") ErrorStatus errorStatus
) implements Serializable {
    public static DrivingSessionResponseDto of(
            final Long drivingSessionId,
            final Long userId,
            final Long carId,
            final LocalDate startDate,
            final LocalTime startTime,
            final LocalDate endDate,
            final LocalTime endTime,
            final LocalDateTime errorTime,
            final LocalDateTime solveTime,
            final DrivingStatus drivingStatus,
            final String latitude,
            final String longitude,
            final ErrorStatus errorStatus
    ) {
        return DrivingSessionResponseDto.builder()
                .drivingSessionId(drivingSessionId)
                .userId(userId)
                .carId(carId)
                .startDate(startDate)
                .startTime(startTime)
                .endDate(endDate)
                .endTime(endTime)
                .errorTime(errorTime)
                .solveTime(solveTime)
                .drivingStatus(drivingStatus)
                .errorStatus(errorStatus)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
