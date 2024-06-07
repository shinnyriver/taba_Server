package taba.tabaServer.tabaserver.dto.sensordatadto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.DrivingHabit;

import java.io.Serializable;

@Builder
public record SensorDataRequestDto(
        @JsonProperty("driving_session_id") Long drivingSessionId,
        @JsonProperty("brake_pressure") double brakePressure,
        @JsonProperty("accel_pressure") double accelPressure,
        @JsonProperty("speed") double speed,
        @JsonProperty("latitude") String latitude,
        @JsonProperty("longitude") String longitude,
        @JsonProperty("driving_habit") DrivingHabit drivingHabit
) implements Serializable {
    public static SensorDataRequestDto of(
            final Long drivingSessionId,
            final double brakePressure,
            final double accelPressure,
            final double speed,
            final String latitude,
            final String longitude,
            final DrivingHabit drivingHabit
    ) {
        return SensorDataRequestDto.builder()
                .drivingSessionId(drivingSessionId)
                .brakePressure(brakePressure)
                .accelPressure(accelPressure)
                .speed(speed)
                .latitude(latitude)
                .longitude(longitude)
                .drivingHabit(drivingHabit)
                .build();
    }
}
