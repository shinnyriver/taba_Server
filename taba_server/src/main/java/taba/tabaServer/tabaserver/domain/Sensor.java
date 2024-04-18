package taba.tabaServer.tabaserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="sensor")
public class Sensor {
    @Id
    @GeneratedValue
    @Column(name = "sensor_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "sensor", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Calibration calibration;

    @Builder
    public Sensor(
            final User user,
            final Calibration calibration
    ) {
        this.user = user;
        this.calibration = calibration;
    }
}
