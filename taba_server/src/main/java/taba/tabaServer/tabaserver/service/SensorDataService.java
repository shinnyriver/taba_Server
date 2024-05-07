package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taba.tabaServer.tabaserver.domain.DrivingSession;
import taba.tabaServer.tabaserver.domain.SensorData;
import taba.tabaServer.tabaserver.dto.sensordatadto.SensorDataRequestDto;
import taba.tabaServer.tabaserver.dto.sensordatadto.SensorDataResponseDto;
import taba.tabaServer.tabaserver.enums.DrivingStatus;
import taba.tabaServer.tabaserver.exception.CommonException;
import taba.tabaServer.tabaserver.exception.ErrorCode;
import taba.tabaServer.tabaserver.repository.DrivingSessionRepository;
import taba.tabaServer.tabaserver.repository.SensorDataRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;
    private final DrivingSessionRepository drivingSessionRepository;

    @Transactional
    public SensorDataResponseDto createSensorData(SensorDataRequestDto sensorDataRequestDto){
        DrivingSession drivingSession = drivingSessionRepository.findById(sensorDataRequestDto.drivingSessionId())
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_DRIVING_SESSION));

        if(drivingSession.getDrivingStatus() != DrivingStatus.DRIVING){
            throw new CommonException(ErrorCode.DRIVING_STATUS_NONE);
        }

        SensorData save = sensorDataRepository.save(SensorData.builder()
                .drivingSession(drivingSession)
                .breakPressure(sensorDataRequestDto.breakPressure())
                .accelPressure(sensorDataRequestDto.accelPressure())
                .speed(sensorDataRequestDto.speed())
                .latitude(sensorDataRequestDto.latitude())
                .longitude(sensorDataRequestDto.longitude())
                .build()
        );

        return SensorDataResponseDto.builder()
                .drivingSessionId(save.getDrivingSession().getId())
                .breakPressure(save.getBreakPressure())
                .accelPressure(save.getAccelPressure())
                .speed(save.getSpeed())
                .latitude(save.getLatitude())
                .longitude(save.getLongitude())
                .build();
    }

    @Transactional
    public Boolean deleteSensorDataById(Long id){
        sensorDataRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean deleteSensorDataByDrivingSessionId(Long id){
        List<SensorData> sensorDataByDrivingSessionId = sensorDataRepository.findSensorDataByDrivingSessionId(id);
        sensorDataRepository.deleteAll(sensorDataByDrivingSessionId);
        return Boolean.TRUE;
    }

    @Transactional
    public List<SensorDataResponseDto> getAllSensorDataByDrivingSessionId(Long id){
        return sensorDataRepository.findSensorDataByDrivingSessionId(id).stream()
                .map(sensorData -> SensorDataResponseDto.of(
                        sensorData.getDrivingSession().getId(),
                        sensorData.getTimestamp(),
                        sensorData.getBreakPressure(),
                        sensorData.getAccelPressure(),
                        sensorData.getSpeed(),
                        sensorData.getLatitude(),
                        sensorData.getLongitude()
                )).collect(Collectors.toList());
    }
}
