package taba.tabaServer.tabaserver.dto.drivingsessiondto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.DrivingStatus;

import java.io.Serializable;

@Builder    //운행종료는 updatedto를 이용하여 구현 하기로 함.
public record DrivingSessionUpdateDto(
        @JsonProperty("driving_status") DrivingStatus drivingStatus
) implements Serializable {
    public static DrivingSessionUpdateDto of(
            final DrivingStatus drivingStatus
    ) {
        return DrivingSessionUpdateDto.builder()
                .drivingStatus(drivingStatus)
                .build();
    }
}
