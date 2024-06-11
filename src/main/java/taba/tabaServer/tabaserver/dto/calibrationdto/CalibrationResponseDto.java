package taba.tabaServer.tabaserver.dto.calibrationdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.SensorType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record CalibrationResponseDto(
        @JsonProperty("id") Long id,
        @JsonProperty("sensor_type") SensorType sensorType,
        @JsonProperty("pressure_max") double pressureMax,
        @JsonProperty("pressure_min") double pressureMin,
        @JsonProperty("created_at") LocalDateTime calibrationTime,
        @JsonProperty("car_id") Long carId
) implements Serializable {
    public static CalibrationResponseDto of(
            final Long id,
            final SensorType sensorType,
            final double pressureMax,
            final double pressureMin,
            final LocalDateTime calibrationTime,
            final Long carId
    ) {
        return CalibrationResponseDto.builder()
                .id(id)
                .sensorType(sensorType)
                .pressureMax(pressureMax)
                .pressureMin(pressureMin)
                .calibrationTime(calibrationTime)
                .carId(carId)
                .build();
    }
}
