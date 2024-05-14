package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taba.tabaServer.tabaserver.domain.Car;
import taba.tabaServer.tabaserver.domain.DrivingSession;
import taba.tabaServer.tabaserver.domain.User;
import taba.tabaServer.tabaserver.dto.calibrationdto.CalibrationResponseDto;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.DrivingSessionErrorOccuredDto;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.DrivingSessionRequestDto;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.DrivingSessionResponseDto;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.DrivingSessionUpdateDto;
import taba.tabaServer.tabaserver.enums.ErrorStatus;
import taba.tabaServer.tabaserver.exception.CommonException;
import taba.tabaServer.tabaserver.exception.ErrorCode;
import taba.tabaServer.tabaserver.repository.CarRepository;
import taba.tabaServer.tabaserver.repository.DrivingSessionRepository;
import taba.tabaServer.tabaserver.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrivingSessionService {

    private final DrivingSessionRepository drivingSessionRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Transactional
    public DrivingSessionResponseDto createDrivingSession(DrivingSessionRequestDto drivingSessionRequestDto){
        User currentUser = userRepository.findById(drivingSessionRequestDto.userId())
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_USER));
        Car currentCar = carRepository.findById(drivingSessionRequestDto.carId())
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_CAR));
        DrivingSession drivingSession = DrivingSession.builder()
                .user(currentUser)
                .car(currentCar)
                .drivingStatus(drivingSessionRequestDto.drivingStatus())
                .errorStatus(ErrorStatus.NORMAL)
                .build();

        drivingSessionRepository.save(drivingSession);

        return DrivingSessionResponseDto.of(
                drivingSession.getUser().getId(),
                drivingSession.getCar().getId(),
                drivingSession.getStartTime(),
                drivingSession.getEndTime(),
                drivingSession.getErrorTime(),
                drivingSession.getDrivingStatus(),
                drivingSession.getErrorStatus()
        );
    }

    @Transactional
    public DrivingSessionResponseDto getDrivingSessionById(Long id){
        DrivingSession drivingSession = drivingSessionRepository.findById(id)
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_DRIVING_SESSION));

        return DrivingSessionResponseDto.of(
                drivingSession.getUser().getId(),
                drivingSession.getCar().getId(),
                drivingSession.getStartTime(),
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
                drivingSession.getUser().getId(),
                drivingSession.getCar().getId(),
                drivingSession.getStartTime(),
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

        drivingSession.errorOccured(drivingSessionErrorOccuredDto.errorStatus());
        drivingSessionRepository.save(drivingSession);

        return DrivingSessionResponseDto.of(
                drivingSession.getUser().getId(),
                drivingSession.getCar().getId(),
                drivingSession.getStartTime(),
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
                        drivingsession.getUser().getId(),
                        drivingsession.getCar().getId(),
                        drivingsession.getStartTime(),
                        drivingsession.getEndTime(),
                        drivingsession.getErrorTime(),
                        drivingsession.getDrivingStatus(),
                        drivingsession.getErrorStatus()
                )).collect(Collectors.toList());
    }

    @Transactional
    public List<DrivingSessionResponseDto> findByErrorStatus(ErrorStatus errorStatus){
        return drivingSessionRepository.findByErrorStatus(errorStatus).stream()
                .map(drivingsession -> DrivingSessionResponseDto.of(
                        drivingsession.getUser().getId(),
                        drivingsession.getCar().getId(),
                        drivingsession.getStartTime(),
                        drivingsession.getEndTime(),
                        drivingsession.getErrorTime(),
                        drivingsession.getDrivingStatus(),
                        drivingsession.getErrorStatus()
                )).collect(Collectors.toList());

    }
}
