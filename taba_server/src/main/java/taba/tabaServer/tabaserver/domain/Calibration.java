package taba.tabaServer.tabaserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "calibration")
public class Calibration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calibration_id")
    private Long id;

    @Column(name = "min_break")
    private double minBreak;

    @Column(name = "max_break")
    private double maxBreak;

    @Column(name = "min_accel")
    private double minAccel;

    @Column(name = "max_accel")
    private double maxAccel;

    @Column(name = "created_at")
    private LocalDateTime calibrationTime;

    @OneToOne()
    @JoinColumn(name="sensor_id", nullable = false, unique = true)
    private Sensor sensor;

    @Builder
    public Calibration(
            final double minBreak,
            final double maxBreak,
            final double minAccel,
            final double maxAccel
    ) {
        this.minBreak = minBreak;
        this.minAccel = minAccel;
        this.maxBreak = maxBreak;
        this.maxAccel = maxAccel;
        this.calibrationTime = LocalDateTime.now();
    }
}
