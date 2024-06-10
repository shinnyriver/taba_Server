package taba.tabaServer.tabaserver.dto.drivingsessiondto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.CarSize;
import taba.tabaServer.tabaserver.enums.ErrorStatus;

import java.io.Serializable;

@Builder
public record ErrorListResponseDto(
        @JsonProperty("id") Long id,
        @JsonProperty("car_size") CarSize carSize,
        @JsonProperty("car_number") String carNumber,
        @JsonProperty("latitude") String latitude,
        @JsonProperty("longitude") String longitude,
        @JsonProperty("status") ErrorStatus status
) implements Serializable {
    public static ErrorListResponseDto of(
            final Long id,
            final CarSize carSize,
            final String carNumber,
            final String latitude,
            final String longitude,
            final ErrorStatus status
    ) {
        return ErrorListResponseDto.builder()
                .id(id)
                .carSize(carSize)
                .carNumber(carNumber)
                .latitude(latitude)
                .longitude(longitude)
                .status(status)
                .build();
    }
}
