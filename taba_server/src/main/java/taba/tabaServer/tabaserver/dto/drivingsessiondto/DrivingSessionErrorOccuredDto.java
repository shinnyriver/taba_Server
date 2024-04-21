package taba.tabaServer.tabaserver.dto.drivingsessiondto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.ErrorStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record DrivingSessionErrorOccuredDto(
        @JsonProperty("errorStatus")ErrorStatus errorStatus
        ) implements Serializable {
            public static DrivingSessionErrorOccuredDto of(
                    final ErrorStatus errorStatus
            ) {
                return DrivingSessionErrorOccuredDto.builder()
                        .errorStatus(errorStatus)
                        .build();
            }
}
