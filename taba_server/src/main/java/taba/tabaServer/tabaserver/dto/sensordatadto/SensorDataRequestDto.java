package taba.tabaServer.tabaserver.dto.sensordatadto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record SensorDataRequestDto(
        @JsonProperty("drivingSessionId") Long drivingSessionId,
        @JsonProperty("brakePressure") double brakePressure,
        @JsonProperty("accelPressure") double accelPressure,
        @JsonProperty("speed") double speed,
        @JsonProperty("latitude") String latitude,
        @JsonProperty("longitude") String longitude
        ) implements Serializable {
            public static SensorDataRequestDto of(
                    final Long drivingSessionId,
                    final double brakePressure,
                    final double accelPressure,
                    final double speed,
                    final String latitude,
                    final String longitude
            ) {
                return SensorDataRequestDto.builder()
                        .drivingSessionId(drivingSessionId)
                        .brakePressure(brakePressure)
                        .accelPressure(accelPressure)
                        .speed(speed)
                        .latitude(latitude)
                        .longitude(longitude)
                        .build();
            }
}
