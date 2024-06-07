package taba.tabaServer.tabaserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taba.tabaServer.tabaserver.enums.DrivingHabit;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sensor_data")
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private DrivingSession drivingSession;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private double brakePressure;

    @Column(nullable = false)
    private double accelPressure;

    @Column(nullable = false)
    private double speed;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Enumerated(EnumType.STRING)
    private DrivingHabit drivingHabit;

    @Builder
    public SensorData(
            final Long id,
            final DrivingSession drivingSession,
            final double brakePressure,
            final double accelPressure,
            final double speed,
            final String latitude,
            final String longitude,
            final DrivingHabit drivingHabit
    ) {
        this.id = id;
        this.drivingSession = drivingSession;
        this.timestamp = LocalDateTime.now();
        this.brakePressure = brakePressure;
        this.accelPressure = accelPressure;
        this.speed = speed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.drivingHabit = drivingHabit;
    }
}
