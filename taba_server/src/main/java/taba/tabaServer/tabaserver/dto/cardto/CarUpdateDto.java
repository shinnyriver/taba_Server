package taba.tabaServer.tabaserver.dto.cardto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.CarSize;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record CarUpdateDto(
        @JsonProperty("carId") Long carId, // ID 필드 추가
        @JsonProperty("carName") String carName,
        @JsonProperty("carSize") CarSize carSize,
        @JsonProperty("totalDistance") int totalDistance,
        @JsonProperty("carNumber") String carNumber,
        @JsonProperty("photo") String photo, // String으로 유지
        @JsonProperty("userId") Long userId,
        @JsonProperty("insurance") String insurance, // 보험 정보 필드 추가
        @JsonProperty("purchaseDate") LocalDate purchaseDate // 구매 일자 필드 추가

        ) implements Serializable {
    //구매일자는 유지
    public static CarResponseDto of(
            final Long carId,
            final String carName,
            final CarSize carSize,
            final int totalDistance,
            final String carNumber,
            final byte[] photo,
            final Long userId,
            final String insurance, // 보험 정보 매개변수 추가
            final LocalDate purchaseDate // 구매일자 필드 추가

    ) {
        return CarResponseDto.builder()
                .carId(carId)
                .carName(carName)
                .carSize(carSize)
                .totalDistance(totalDistance)
                .carNumber(carNumber)
                .photo(photo)
                .userId(userId)
                .insurance(insurance) // 보험 정보 설정
                .purchaseDate(purchaseDate)
                .build();
    }
}
