package taba.tabaServer.tabaserver.dto.calibrationdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.domain.Car;
import taba.tabaServer.tabaserver.enums.SensorType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record CalibrationDto(
        @JsonProperty("sensorType") SensorType sensorType,
        @JsonProperty("pressureMax") double pressureMax,
        @JsonProperty("pressureMin") double pressureMin,
        @JsonProperty("createdAt")LocalDateTime calibrationTime,
        @JsonProperty("carId")Long carId

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
                        .calibrationTime(LocalDateTime.now())
                        .carId(carId)
                        .build();
            }

}
