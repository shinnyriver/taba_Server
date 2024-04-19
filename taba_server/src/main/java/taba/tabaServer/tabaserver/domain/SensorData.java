package taba.tabaServer.tabaserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="sensor_data")
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
    private double breakPressure;

    @Column(nullable = false)
    private double accelPressure;

    @Column(nullable = false)
    private double speed;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Builder
    public SensorData(
            final DrivingSession drivingSession,
            final LocalDateTime timestamp,
            final double breakPressure,
            final double accelPressure,
            final double speed,
            final String latitude,
            final String longitude
    ) {
        this.drivingSession = drivingSession;
        this.timestamp = timestamp;
        this.breakPressure = breakPressure;
        this.accelPressure = accelPressure;
        this.speed = speed;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
