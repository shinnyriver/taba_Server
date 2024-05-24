package taba.tabaServer.tabaserver.dto.cardto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.CarSize;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
public record CarResponseDto(
        @JsonProperty("carId") Long carId, // ID 필드 추가
        @JsonProperty("carName") String carName,
        @JsonProperty("carSize") CarSize carSize,
        @JsonProperty("totalDistance") int totalDistance,
        @JsonProperty("carNumber") String carNumber,
        @JsonProperty("photo") byte[] photo,
        @JsonProperty("userId") Long userId,
        @JsonProperty("insurance") String insurance, // 보험 정보 필드 추가
        @JsonProperty("purchaseDate") LocalDate purchaseDate // 구매 일자 필드 추가

        ) implements Serializable {
            public static CarResponseDto of(
                    final Long carId,
                    final String carName,
                    final CarSize carSize,
                    final int totalDistance,
                    final String carNumber,
                    final Long userid,
                    final byte[] photo,
                    final String insurance,
                    final LocalDate purchaseDate
            ) {
                return CarResponseDto.builder()
                        .carId(carId)
                        .carName(carName)
                        .carSize(carSize)
                        .totalDistance(totalDistance)
                        .carNumber(carNumber)
                        .photo(photo)
                        .userId(userid)
                        .insurance(insurance)
                        .purchaseDate(purchaseDate)
                        .build();
            }
}
