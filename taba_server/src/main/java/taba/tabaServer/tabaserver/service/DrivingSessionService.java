package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import taba.tabaServer.tabaserver.repository.DrivingSessionRepository;
import taba.tabaServer.tabaserver.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrivingSessionService {

    private final DrivingSessionRepository drivingSessionRepository;
    private final UserRepository userRepository;

    @Transactional
    public DrivingSessionResponseDto createDrivingSession(DrivingSessionRequestDto drivingSessionRequestDto){
        User currentUser = userRepository.findById(drivingSessionRequestDto.userId())
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_USER));
        DrivingSession drivingSession = DrivingSession.builder()
                .user(currentUser)
                .startTime(drivingSessionRequestDto.startTime())
                .drivingStatus(drivingSessionRequestDto.drivingStatus())
                .errorStatus(ErrorStatus.NORMAL)
                .build();

        return DrivingSessionResponseDto.of(
                drivingSession.getUser().getId(),
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
                        drivingsession.getStartTime(),
                        drivingsession.getEndTime(),
                        drivingsession.getErrorTime(),
                        drivingsession.getDrivingStatus(),
                        drivingsession.getErrorStatus()
                )).collect(Collectors.toList());
    }
}
