package taba.tabaServer.tabaserver.dto.sensordatadto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.DrivingHabit;
import taba.tabaServer.tabaserver.enums.ErrorStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record SensorDataResponseDto(
        @JsonProperty("sensor_id") Long sensorId,
        @JsonProperty("driving_session_id") Long drivingSessionId,
        @JsonProperty("timestamp") LocalDateTime timeStamp,
        @JsonProperty("brake_pressure") double brakePressure,
        @JsonProperty("accel_pressure") double accelPressure,
        @JsonProperty("speed") double speed,
        @JsonProperty("latitude") String latitude,
        @JsonProperty("longitude") String longitude,
        @JsonProperty("error_status") ErrorStatus errorStatus,
        @JsonProperty("driving_habit") DrivingHabit drivingHabit //운전 습관 필드 추가

) implements Serializable {
    public static SensorDataResponseDto of(
            final Long sensorId,
            final Long drivingSessionId,
            final LocalDateTime timeStamp,
            final double brakePressure,
            final double accelPressure,
            final double speed,
            final String latitude,
            final String longitude,
            final ErrorStatus errorStatus,
            final DrivingHabit drivingHabit //운전 습관 필드 추가
    ) {
        return SensorDataResponseDto.builder()
                .sensorId(sensorId)
                .drivingSessionId(drivingSessionId)
                .timeStamp(timeStamp)
                .brakePressure(brakePressure)
                .accelPressure(accelPressure)
                .speed(speed)
                .latitude(latitude)
                .longitude(longitude)
                .errorStatus(errorStatus)
                .drivingHabit(drivingHabit)
                .build();
    }
}
