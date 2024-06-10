package taba.tabaServer.tabaserver.dto.cardto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import taba.tabaServer.tabaserver.enums.CarSize;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
public record CarUpdateDto(
        @JsonProperty("car_id") Long carId, // ID 필드 추가
        @JsonProperty("car_name") String carName,
        @JsonProperty("car_size") CarSize carSize,
        @JsonProperty("total_distance") int totalDistance,
        @JsonProperty("car_number") String carNumber,
        @JsonProperty("photo") String photo, // String으로 유지
        @JsonProperty("user_id") Long userId,
        @JsonProperty("insurance") String insurance, // 보험 정보 필드 추가
        @JsonProperty("purchase_date") LocalDate purchaseDate, // 구매 일자 필드 추가
        @JsonProperty("driving_score") int drivingScore //운전 점수 필드 추가

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
            final LocalDate purchaseDate, // 구매일자 필드 추가
            final int drivingScore //운전 점수 필드 추가

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
                .drivingScore(drivingScore)
                .build();
    }
}
