package taba.tabaServer.tabaserver.dto.sensordatadto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record SensorDataResponseDto(
        @JsonProperty("drivingSessionId") Long drivingSessionId,
        @JsonProperty("timeStamp")LocalDateTime timeStamp,
        @JsonProperty("breakPressure") double breakPressure,
        @JsonProperty("accelPressure") double accelPressure,
        @JsonProperty("speed") double speed,
        @JsonProperty("latitude") String latitude,
        @JsonProperty("longitude") String longitude
) implements Serializable {
    public static SensorDataResponseDto of(
            final Long drivingSessionId,
            final LocalDateTime timeStamp,
            final double breakPressure,
            final double accelPressure,
            final double speed,
            final String latitude,
            final String longitude
    ) {
        return SensorDataResponseDto.builder()
                .drivingSessionId(drivingSessionId)
                .timeStamp(timeStamp)
                .breakPressure(breakPressure)
                .accelPressure(accelPressure)
                .speed(speed)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
