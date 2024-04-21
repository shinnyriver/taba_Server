package taba.tabaServer.tabaserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taba.tabaServer.tabaserver.enums.DrivingStatus;
import taba.tabaServer.tabaserver.enums.ErrorStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="driving_session")
public class DrivingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private LocalDateTime errorTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DrivingStatus drivingStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ErrorStatus errorStatus;

    @Builder
    public DrivingSession(
            final User user,
            final LocalDateTime startTime,
            final DrivingStatus drivingStatus,
            final ErrorStatus errorStatus
    ) {
        this.user = user;
        this.startTime = LocalDateTime.now();
        this.drivingStatus = drivingStatus;
        this.errorStatus = errorStatus;
    }

    public void errorOccured(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
        this.errorTime = LocalDateTime.now();
    }

    public void endSession(DrivingStatus drivingStatus) {
        this.drivingStatus = drivingStatus;
        this.endTime = LocalDateTime.now();
    }
}
