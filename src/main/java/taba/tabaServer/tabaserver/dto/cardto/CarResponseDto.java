package taba.tabaServer.tabaserver.dto.cardto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.CarSize;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
public record CarResponseDto(
        @JsonProperty("car_id") Long carId, // ID 필드 추가
        @JsonProperty("car_name") String carName,
        @JsonProperty("car_size") CarSize carSize,
        @JsonProperty("total_distance") int totalDistance,
        @JsonProperty("car_number") String carNumber,
        @JsonProperty("photo") byte[] photo,
        @JsonProperty("user_id") Long userId,
        @JsonProperty("insurance") String insurance, // 보험 정보 필드 추가
        @JsonProperty("purchase_date") LocalDate purchaseDate, // 구매 일자 필드 추가
        @JsonProperty("driving_score") int drivingScore  // 운전 점수 필드 추가

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
            final LocalDate purchaseDate,
            final int drivingScore // 운전 점수 필드 추가

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
                .drivingScore(drivingScore) // 운전 점수 필드 추가
                .build();
    }
}
