package taba.tabaServer.tabaserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.DrivingSessionErrorOccuredDto;
import taba.tabaServer.tabaserver.enums.DrivingStatus;
import taba.tabaServer.tabaserver.enums.ErrorStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "driving_session")
public class DrivingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column
    private LocalDate endDate;

    @Column
    private LocalTime endTime;

    @Column
    private LocalDateTime errorTime;

    @Column
    private LocalDateTime solveTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DrivingStatus drivingStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ErrorStatus errorStatus;

    @Column(name = "error_latitude", nullable = true)
    private String errorLatitude;

    @Column(name = "error_longitude", nullable = true)
    private String errorLongitude;

    @Builder
    public DrivingSession(
            final User user,
            final Car car,
            final LocalDate startDate,
            final LocalTime startTime,
            final DrivingStatus drivingStatus,
            final ErrorStatus errorStatus
    ) {
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.startTime = startTime;
        this.drivingStatus = drivingStatus;
        this.errorStatus = errorStatus;
    }

    public void errorOccurred(DrivingSessionErrorOccuredDto drivingSessionErrorOccuredDto) {
        this.errorStatus = drivingSessionErrorOccuredDto.errorStatus();
        this.errorTime = LocalDateTime.now();
        this.errorLatitude = drivingSessionErrorOccuredDto.latitude();
        this.errorLongitude = drivingSessionErrorOccuredDto.longitude();
    }

    public void endSession(DrivingStatus drivingStatus) {
        this.endDate = LocalDate.now();
        this.endTime = LocalTime.now();
        this.drivingStatus = drivingStatus;
    }

    public void solveSession() {
        this.errorStatus = ErrorStatus.SOLVE;
        this.solveTime = LocalDateTime.now();
    }
}
