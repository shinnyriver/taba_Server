package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import taba.tabaServer.tabaserver.domain.Calibration;
import taba.tabaServer.tabaserver.domain.Car;
import taba.tabaServer.tabaserver.domain.DrivingSession;
import taba.tabaServer.tabaserver.domain.User;
import taba.tabaServer.tabaserver.dto.aidto.FlaskDrivingSessionDto;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.*;
import taba.tabaServer.tabaserver.enums.ErrorStatus;
import taba.tabaServer.tabaserver.enums.SensorType;
import taba.tabaServer.tabaserver.exception.CommonException;
import taba.tabaServer.tabaserver.exception.ErrorCode;
import taba.tabaServer.tabaserver.repository.CalibrationRepository;
import taba.tabaServer.tabaserver.repository.CarRepository;
import taba.tabaServer.tabaserver.repository.DrivingSessionRepository;
import taba.tabaServer.tabaserver.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrivingSessionService {

    private final DrivingSessionRepository drivingSessionRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final CalibrationRepository calibrationRepository;
    private final WebClient webClient;

    @Transactional
    public DrivingSessionResponseDto createDrivingSession(DrivingSessionRequestDto drivingSessionRequestDto){
        User currentUser = userRepository.findById(drivingSessionRequestDto.userId())
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_USER));
        Car currentCar = carRepository.findById(drivingSessionRequestDto.carId())
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_CAR));

        /**
         * 데이터 무결성 방지(운전 시작의 시각 및 날짜가 nullable로 설정)
         */
        LocalDate today = LocalDate.now(); // 오늘 날짜
        LocalTime now = LocalTime.now(); // 현재 시간

        DrivingSession drivingSession = DrivingSession.builder()
                .user(currentUser)
                .car(currentCar)
                .startDate(today)
                .startTime(now)
                .drivingStatus(drivingSessionRequestDto.drivingStatus())
                .errorStatus(ErrorStatus.NORMAL)
                .build();

        Calibration accelCalibration = calibrationRepository.findByCar_CarIdAndSensorType(currentCar.getCarId(), SensorType.ACCEL)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CALIBRATION));

        Calibration brakeCalibration = calibrationRepository.findByCar_CarIdAndSensorType(currentCar.getCarId(), SensorType.BRAKE)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CALIBRATION));


        drivingSessionRepository.save(drivingSession);

        sendDrivingSessionToFlask(FlaskDrivingSessionDto.of(
                currentCar.getCarId(),
                accelCalibration.getSensorType(),
                accelCalibration.getPressureMax()
        ));

        sendDrivingSessionToFlask(FlaskDrivingSessionDto.of(
                currentCar.getCarId(),
                brakeCalibration.getSensorType(),
                brakeCalibration.getPressureMax()
        ));

        return DrivingSessionResponseDto.of(
                drivingSession.getId(),
                drivingSession.getUser().getId(),
                drivingSession.getCar().getCarId(),
                drivingSession.getStartDate(),
                drivingSession.getStartTime(),
                drivingSession.getEndDate(),
                drivingSession.getEndTime(),
                drivingSession.getErrorTime(),
                drivingSession.getDrivingStatus(),
                drivingSession.getErrorStatus()
        );
    }

    private void sendDrivingSessionToFlask(FlaskDrivingSessionDto flaskDrivingSessionDto){
        webClient.post()
                .uri("http://127.0.0.1:5000/calibration")
                .body(Mono.just(flaskDrivingSessionDto), FlaskDrivingSessionDto.class)
                .retrieve();

        System.out.println("Flask Server Response");
    }

    @Transactional
    public DrivingSessionResponseDto getDrivingSessionById(Long id){
        DrivingSession drivingSession = drivingSessionRepository.findById(id)
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_DRIVING_SESSION));

        return DrivingSessionResponseDto.of(
                drivingSession.getId(),
                drivingSession.getUser().getId(),
                drivingSession.getCar().getCarId(),
                drivingSession.getStartDate(),
                drivingSession.getStartTime(),
                drivingSession.getEndDate(),
                drivingSession.getEndTime(),
                drivingSession.getErrorTime(),
                drivingSession.getDrivingStatus(),
                drivingSession.getErrorStatus()
        );
    }

    @Transactional  //운행 종료 업데이트 메소드.
    public DrivingSessionResponseDto endDrivingSession(Long id, DrivingSessionUpdateDto drivingSessionUpdateDto){
        DrivingSession drivingSession = drivingSessionRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DRIVING_SESSION));

        drivingSession.endSession(drivingSessionUpdateDto.drivingStatus());
        drivingSessionRepository.save(drivingSession);

        return DrivingSessionResponseDto.of(
                drivingSession.getId(),
                drivingSession.getUser().getId(),
                drivingSession.getCar().getCarId(),
                drivingSession.getStartDate(),
                drivingSession.getStartTime(),
                drivingSession.getEndDate(),
                drivingSession.getEndTime(),
                drivingSession.getErrorTime(),
                drivingSession.getDrivingStatus(),
                drivingSession.getErrorStatus()
        );
    }

    @Transactional  //에러 감지시 로직
    public DrivingSessionResponseDto drivingSessionErrorOccured(
            Long id, DrivingSessionErrorOccuredDto drivingSessionErrorOccuredDto){
        DrivingSession drivingSession = drivingSessionRepository.findById(id)
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_DRIVING_SESSION));

        drivingSession.errorOccurred(drivingSessionErrorOccuredDto);
        drivingSessionRepository.save(drivingSession);

        return DrivingSessionResponseDto.of(
                drivingSession.getId(),
                drivingSession.getUser().getId(),
                drivingSession.getCar().getCarId(),
                drivingSession.getStartDate(),
                drivingSession.getStartTime(),
                drivingSession.getEndDate(),
                drivingSession.getEndTime(),
                drivingSession.getErrorTime(),
                drivingSession.getDrivingStatus(),
                drivingSession.getErrorStatus()
        );
    }

    @Transactional
    public Boolean deleteDrivingSession(Long id){
        drivingSessionRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Transactional
    public List<DrivingSessionResponseDto> getAllDrivingSessionByUserId(Long userId){
        return drivingSessionRepository.findDrivingSessionByUserId(userId).stream()
                .map(drivingsession -> DrivingSessionResponseDto.of(
                        drivingsession.getId(),
                        drivingsession.getUser().getId(),
                        drivingsession.getCar().getCarId(),
                        drivingsession.getStartDate(),
                        drivingsession.getStartTime(),
                        drivingsession.getEndDate(),
                        drivingsession.getEndTime(),
                        drivingsession.getErrorTime(),
                        drivingsession.getDrivingStatus(),
                        drivingsession.getErrorStatus()
                )).collect(Collectors.toList());
    }

    @Transactional
    public List<DrivingSessionResponseDto> findAllByErrorStatus(ErrorStatus errorStatus){
        return drivingSessionRepository.findAllByErrorStatus(errorStatus).stream()
                .map(drivingsession -> DrivingSessionResponseDto.of(
                        drivingsession.getId(),
                        drivingsession.getUser().getId(),
                        drivingsession.getCar().getCarId(),
                        drivingsession.getStartDate(),
                        drivingsession.getStartTime(),
                        drivingsession.getEndDate(),
                        drivingsession.getEndTime(),
                        drivingsession.getErrorTime(),
                        drivingsession.getDrivingStatus(),
                        drivingsession.getErrorStatus()
                )).collect(Collectors.toList());

    }

    @Transactional
    public List<DrivingSession> getSessionsBetweenDates(LocalDate start, LocalDate end) {
        return drivingSessionRepository.findAllByStartDateBetween(start, end);
    }

    @Transactional
    public List<ErrorListResponseDto> getErrorList(){
        List<ErrorStatus> statuses = Arrays.asList(ErrorStatus.ERROR, ErrorStatus.SOLVE);
        return drivingSessionRepository.findAllByErrorStatusIn(statuses).stream()
                .map(drivingsession -> ErrorListResponseDto.of(
                        drivingsession.getId(),
                        drivingsession.getCar().getCarSize(),
                        drivingsession.getCar().getCarNumber(),
                        drivingsession.getErrorLatitude(),
                        drivingsession.getErrorLongitude(),
                        drivingsession.getErrorStatus()
                )).collect(Collectors.toList());
    }

}