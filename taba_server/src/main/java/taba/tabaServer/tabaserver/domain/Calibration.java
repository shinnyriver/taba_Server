package taba.tabaServer.tabaserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taba.tabaServer.tabaserver.enums.SensorType;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "calibration")
public class Calibration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calibration_id")
    private Long id;

    @Column(name = "sensor_type")
    @Enumerated(EnumType.STRING)
    private SensorType sensorType;

    @Column(name = "pressure_max")
    private double pressureMax;

    @Column(name = "pressure_min")
    private double pressureMin;

    @Column(name = "created_at")
    private LocalDateTime calibrationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @Builder
    public Calibration(
            final SensorType sensorType,
            final double pressureMax,
            final double pressureMin,
            final Car car
    ) {
        this.sensorType = sensorType;
        this.pressureMax = pressureMax;
        this.pressureMin = pressureMin;
        this.calibrationTime = LocalDateTime.now();
        this.car = car;
    }

    public void updateCalibration(
            final SensorType sensorType,
            final double pressureMax,
            final double pressureMin,
            final Car car
    ) {
        this.sensorType = sensorType;
        this.pressureMax = pressureMax;
        this.pressureMin = pressureMin;
        this.calibrationTime = LocalDateTime.now();
        this.car = car;
    }

}
