package taba.tabaServer.tabaserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taba.tabaServer.tabaserver.enums.CarSize;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @Column(name = "car_name", nullable = false)
    private String carName;

    @Column(name="car_size", nullable = false)
    @Enumerated(EnumType.STRING)
    private CarSize carSize;

    @Column(name = "total_distance")
    private int totalDistance;

    @Column(name = "car_number", nullable = false)
    private String carNumber;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Car(
            final String carName,
            final CarSize carSize,
            final int totalDistance,
            final String carNumber,
            final byte[] photo,
            final User user
    ) {
        this.carName = carName;
        this.carSize = carSize;
        this.totalDistance = totalDistance;
        this.carNumber = carNumber;
        this.photo = photo;
        this.user = user;
    }

    public void updateCar(
            String carName,
            CarSize carSize,
            int totalDistance,
            String carNumber
    ) {
        this.carName = carName;
        this.carSize = carSize;
        this.totalDistance = totalDistance;
        this.carNumber = carNumber;
    }
}
