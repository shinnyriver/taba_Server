package taba.tabaServer.tabaserver.dto.aidto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record FlaskSensorDataDto(
        @JsonProperty("driving_session_id") Long drivingSessionId,
        @JsonProperty("brake_pressure") double brakePressure,
        @JsonProperty("accel_pressure") double accelPressure,
        @JsonProperty("speed") double speed
) implements Serializable {
    public static FlaskSensorDataDto of(
            final Long drivingSessionId,
            final double brakePressure,
            final double accelPressure,
            final double speed
    ) {
        return FlaskSensorDataDto.builder()
                .drivingSessionId(drivingSessionId)
                .brakePressure(brakePressure)
                .accelPressure(accelPressure)
                .speed(speed)
                .build();
    }
}
