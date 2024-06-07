package taba.tabaServer.tabaserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taba.tabaServer.tabaserver.enums.CarSize;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long carId;

    @Column(name = "car_name", nullable = false)
    private String carName;

    @Column(name = "car_size", nullable = false)
    @Enumerated(EnumType.STRING)
    private CarSize carSize;

    @Column(name = "total_distance")
    private int totalDistance;

    @Column(name = "car_number", nullable = false)
    private String carNumber;

    @Lob
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "insurance")
    private String insurance; // 보험 정보 필드 추가

    @Column(name = "purchase_date")
    private LocalDate purchaseDate; // 구매일자 필드 추가

    @Column(name = "driving_score")
    private int drivingScore; // 운전점수 필드 추가


    @Builder
    public Car(
            final Long carId,
            final String carName,
            final CarSize carSize,
            final int totalDistance,
            final String carNumber,
            final byte[] photo,
            final String insurance, // 보험 정보 필드 추가
            final LocalDate purchaseDate, // 구매일자 필드 추가
            final int drivingScore, // 운전 점수 필드 추가
            final User user
    ) {
        this.carId = carId;
        this.carName = carName;
        this.carSize = carSize;
        this.totalDistance = totalDistance;
        this.carNumber = carNumber;
        this.photo = photo;
        this.insurance = insurance;
        this.purchaseDate = purchaseDate; // 구매일자 필드 추가
        this.drivingScore = drivingScore;
        this.user = user;
    }

    public void updateCar(
            Long carId,
            String carName,
            CarSize carSize,
            int totalDistance,
            String carNumber,
            byte[] photo,
            String insurance,
            LocalDate purchaseDate, // 구매일자 필드 추가
            int drivingScore //운전 점수 필드 추가
    ) {
        this.carId = carId;
        this.carName = carName;
        this.carSize = carSize;
        this.totalDistance = totalDistance;
        this.carNumber = carNumber;
        this.photo = photo;
        this.insurance = insurance;
        this.purchaseDate = purchaseDate; // 구매일자 필드 추가
        this.drivingScore = drivingScore; //운전 점수 필드 추가
    }

    //차량 점수 업데이트 메서드
    public void updateCarScore(int drivingScore) {
        this.drivingScore = drivingScore;
    }
}
