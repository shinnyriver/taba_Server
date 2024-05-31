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
        @JsonProperty("user_id") Long userId,
        @JsonProperty("car_id") Long carId,
        @JsonProperty("start_date") LocalDate startDate,
        @JsonProperty("start_time") LocalTime startTime,
        @JsonProperty("end_date") LocalDate endDate,
        @JsonProperty("end_time") LocalTime endTime,
        //스네이크 케이스로 전환(errorTime -> error_time)
        @JsonProperty("error_time") LocalDateTime errorTime,
        @JsonProperty("drivingStatus") DrivingStatus drivingStatus,
        @JsonProperty("errorStatus")ErrorStatus errorStatus
        ) implements Serializable {
            public static DrivingSessionResponseDto of(
                    final Long userId,
                    final Long carId,
                    final LocalDate startDate,
                    final LocalTime startTime,
                    final LocalDate endDate,
                    final LocalTime endTime,
                    final LocalDateTime errorTime,
                    final DrivingStatus drivingStatus,
                    final ErrorStatus errorStatus
            ) {
                return DrivingSessionResponseDto.builder()
                        .userId(userId)
                        .carId(carId)
                        .startDate(startDate)
                        .startTime(startTime)
                        .endDate(endDate)
                        .endTime(endTime)
                        .errorTime(errorTime)
                        .drivingStatus(drivingStatus)
                        .errorStatus(errorStatus)
                        .build();
            }
}
