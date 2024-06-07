package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import taba.tabaServer.tabaserver.domain.DrivingSession;
import taba.tabaServer.tabaserver.domain.SensorData;
import taba.tabaServer.tabaserver.dto.aidto.FlaskResponseDto;
import taba.tabaServer.tabaserver.dto.aidto.FlaskSensorDataDto;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.DrivingSessionErrorOccuredDto;
import taba.tabaServer.tabaserver.dto.sensordatadto.SensorDataRequestDto;
import taba.tabaServer.tabaserver.dto.sensordatadto.SensorDataResponseDto;
import taba.tabaServer.tabaserver.enums.DrivingStatus;
import taba.tabaServer.tabaserver.enums.ErrorStatus;
import taba.tabaServer.tabaserver.exception.CommonException;
import taba.tabaServer.tabaserver.exception.ErrorCode;
import taba.tabaServer.tabaserver.repository.DrivingSessionRepository;
import taba.tabaServer.tabaserver.repository.SensorDataRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;
    private final DrivingSessionRepository drivingSessionRepository;
    private final WebClient webClient;
    private final DrivingSessionService drivingSessionService;

    @Transactional
    public SensorDataResponseDto createSensorData(SensorDataRequestDto sensorDataRequestDto) {
        DrivingSession drivingSession = drivingSessionRepository.findById(sensorDataRequestDto.drivingSessionId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DRIVING_SESSION));

        if (drivingSession.getDrivingStatus() != DrivingStatus.DRIVING) {
            throw new CommonException(ErrorCode.DRIVING_STATUS_NONE);
        }

        SensorData save = sensorDataRepository.save(SensorData.builder()
                .drivingSession(drivingSession)
                .brakePressure(sensorDataRequestDto.brakePressure())
                .accelPressure(sensorDataRequestDto.accelPressure())
                .speed(sensorDataRequestDto.speed())
                .latitude(sensorDataRequestDto.latitude())
                .longitude(sensorDataRequestDto.longitude())
                .drivingHabit(sensorDataRequestDto.drivingHabit()) //운전 습관 필드 추가
                .build()
        );

        sendSensorDataToFlask(FlaskSensorDataDto.of(
                        drivingSession.getId(),
                        save.getBrakePressure(),
                        save.getAccelPressure(),
                        save.getSpeed()
                ),
                save
        );

        return SensorDataResponseDto.builder()
                .sensorId(save.getId())
                .drivingSessionId(save.getDrivingSession().getId())
                .brakePressure(save.getBrakePressure())
                .accelPressure(save.getAccelPressure())
                .timeStamp(save.getTimestamp())
                .speed(save.getSpeed())
                .latitude(save.getLatitude())
                .longitude(save.getLongitude())
                .errorStatus(drivingSession.getErrorStatus())
                .drivingHabit(save.getDrivingHabit())
                .build();
    }

    private void sendSensorDataToFlask(FlaskSensorDataDto flaskSensorDataDto, SensorData sensorData) {
        webClient.post()
                .uri("http://localhost:5000/predict")
                .body(Mono.just(flaskSensorDataDto), FlaskSensorDataDto.class)
                .retrieve()
                .bodyToMono(FlaskResponseDto.class)
                .subscribe(response -> {
                    if ("ERROR".equals(response.result())) {
                        drivingSessionService.drivingSessionErrorOccured(
                                flaskSensorDataDto.drivingSessionId(),
                                DrivingSessionErrorOccuredDto.of(
                                        ErrorStatus.ERROR,
                                        sensorData.getLatitude(),
                                        sensorData.getLongitude()
                                )
                        );
                    }
                }, error -> {
                    System.err.println("Error connecting to Flask server: " + error.getMessage());
                });
    }

    @Transactional
    public Boolean deleteSensorDataById(Long id) {
        sensorDataRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean deleteSensorDataByDrivingSessionId(Long id) {
        List<SensorData> sensorDataByDrivingSessionId = sensorDataRepository.findSensorDataByDrivingSessionId(id);
        sensorDataRepository.deleteAll(sensorDataByDrivingSessionId);
        return Boolean.TRUE;
    }

    @Transactional
    public List<SensorDataResponseDto> getAllSensorDataByDrivingSessionId(Long id) {
        return sensorDataRepository.findSensorDataByDrivingSessionId(id).stream()
                .map(sensorData -> SensorDataResponseDto.of(
                        sensorData.getId(),
                        sensorData.getDrivingSession().getId(),
                        sensorData.getTimestamp(),
                        sensorData.getBrakePressure(),
                        sensorData.getAccelPressure(),
                        sensorData.getSpeed(),
                        sensorData.getLatitude(),
                        sensorData.getLongitude(),
                        sensorData.getDrivingSession().getErrorStatus(),
                        sensorData.getDrivingHabit()
                )).collect(Collectors.toList());
    }

    @Transactional
    public ByteArrayInputStream getSensorDataAsCsvForSession(Long sessionId) {
        Optional<DrivingSession> sessionOptional = drivingSessionRepository.findById(sessionId);
        if (sessionOptional.isPresent() && sessionOptional.get().getErrorStatus() == ErrorStatus.ERROR) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintWriter writer = new PrintWriter(out);
            writer.println("SessionId,Timestamp,BrakePressure,AccelPressure,Speed,Latitude,Longitude");

            List<SensorData> sensorDataList = sensorDataRepository.findAllByDrivingSession(sessionOptional.get());
            for (SensorData data : sensorDataList) {
                writer.printf("%d,%s,%f,%f,%f,%s,%s\n",
                        sessionOptional.get().getId(), data.getTimestamp(),
                        data.getBrakePressure(), data.getAccelPressure(),
                        data.getSpeed(), data.getLatitude(), data.getLongitude());
            }
            writer.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } else {
            return null;
        }
    }
}
