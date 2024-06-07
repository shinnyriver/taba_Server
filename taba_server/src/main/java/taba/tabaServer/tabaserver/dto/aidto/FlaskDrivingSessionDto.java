package taba.tabaServer.tabaserver.dto.aidto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.SensorType;

import java.io.Serializable;

@Builder
public record FlaskDrivingSessionDto(
        @JsonProperty("car_id") Long carId,
        @JsonProperty("sensor_type") SensorType sensorType,
        @JsonProperty("pressure_max") double pressureMax
) implements Serializable {
    public static FlaskDrivingSessionDto of(
            final Long carId,
            final SensorType sensorType,
            final double pressureMax
    ) {
        return FlaskDrivingSessionDto.builder()
                .carId(carId)
                .sensorType(sensorType)
                .pressureMax(pressureMax)
                .build();
    }
}
