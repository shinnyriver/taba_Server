package taba.tabaServer.tabaserver.dto.calibrationdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.SensorType;

import java.io.Serializable;

@Builder
public record CalibrationDto(
        @JsonProperty("sensor_type") SensorType sensorType,
        @JsonProperty("pressure_max") double pressureMax,
        @JsonProperty("pressure_min") double pressureMin,
        @JsonProperty("car_id") Long carId

) implements Serializable {
    public static CalibrationDto of(
            final SensorType sensorType,
            final double pressureMax,
            final double pressureMin,
            final Long carId
    ) {
        return CalibrationDto.builder()
                .sensorType(sensorType)
                .pressureMax(pressureMax)
                .pressureMin(pressureMin)
                .carId(carId)
                .build();
    }

}
