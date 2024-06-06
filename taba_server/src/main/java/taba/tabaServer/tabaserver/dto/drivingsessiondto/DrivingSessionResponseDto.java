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
        //스네이크 케이스로 전환(errorTime -> error_time)
        @JsonProperty("error_time") LocalDateTime errorTime,
        @JsonProperty("solve_time") LocalDateTime solveTime,
        @JsonProperty("drivingStatus") DrivingStatus drivingStatus,
        @JsonProperty("error_latitude") String errorLatitude, //에러 위도 추가
        @JsonProperty("error_longitude")String errorLongitude,//에러 경도 추가
        @JsonProperty("errorStatus")ErrorStatus errorStatus
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
                    final String errorLatitude,
                    final String errorLongitude,
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
                        .errorLatitude(errorLatitude)
                        .errorLongitude(errorLongitude)
                        .errorStatus(errorStatus)
                        .build();
            }
}
