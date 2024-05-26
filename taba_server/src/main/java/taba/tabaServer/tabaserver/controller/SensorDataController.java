package taba.tabaServer.tabaserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taba.tabaServer.tabaserver.dto.global.ResponseDto;
import taba.tabaServer.tabaserver.dto.sensordatadto.SensorDataRequestDto;
import taba.tabaServer.tabaserver.exception.CommonException;
import taba.tabaServer.tabaserver.exception.ErrorCode;
import taba.tabaServer.tabaserver.service.SensorDataService;

import java.io.ByteArrayInputStream;


@RestController
@RequestMapping("/api/sensordata")
@RequiredArgsConstructor
public class SensorDataController {

    private final SensorDataService sensorDataService;

    @PostMapping
    public ResponseDto<?> createSensorData(@RequestBody SensorDataRequestDto sensorDataRequestDto){
        return ResponseDto.ok(sensorDataService.createSensorData(sensorDataRequestDto));
    }

    @GetMapping("/{drivingSessionId}")
    public ResponseDto<?> getAllSensorDataByDrivingSessionId(@PathVariable Long drivingSessionId){
        return ResponseDto.ok(sensorDataService.getAllSensorDataByDrivingSessionId(drivingSessionId));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteSensorDataById(@PathVariable Long id){
        return ResponseDto.ok(sensorDataService.deleteSensorDataById(id));
    }

    @DeleteMapping("/sessionid/{id}")
    public ResponseDto<?> deleteSensorDataBySessionId(@PathVariable Long id){
        return ResponseDto.ok(sensorDataService.deleteSensorDataByDrivingSessionId(id));
    }

    @GetMapping("/session/{sessionId}/csv") //특정 drivingsession id를 통한 csv 전송
    public ResponseEntity<ByteArrayInputStream> downloadSessionData(@PathVariable Long sessionId){
        ByteArrayInputStream stream = sensorDataService.getSensorDataAsCsvForSession(sessionId);
        if(stream == null){
            return ResponseEntity.notFound().build();
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition",
                    "attachment; filename=session_" + sessionId + "_data.csv");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(stream);
        }
    }
}
