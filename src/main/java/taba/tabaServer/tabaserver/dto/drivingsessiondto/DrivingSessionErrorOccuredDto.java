package taba.tabaServer.tabaserver.dto.drivingsessiondto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.ErrorStatus;

import java.io.Serializable;

@Builder
public record DrivingSessionErrorOccuredDto(
        @JsonProperty("error_status") ErrorStatus errorStatus,
        @JsonProperty("latitude") String latitude,
        @JsonProperty("longitude") String longitude
) implements Serializable {
    public static DrivingSessionErrorOccuredDto of(
            final ErrorStatus errorStatus,
            final String latitude,
            final String longitude
    ) {
        return DrivingSessionErrorOccuredDto.builder()
                .errorStatus(errorStatus)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
