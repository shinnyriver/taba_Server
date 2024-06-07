package taba.tabaServer.tabaserver.controller;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taba.tabaServer.tabaserver.dto.global.ResponseDto;
import taba.tabaServer.tabaserver.dto.sensordatadto.SensorDataRequestDto;
import taba.tabaServer.tabaserver.service.SensorDataService;

import java.io.ByteArrayInputStream;


@RestController
@RequestMapping("/api/sensordata")
@RequiredArgsConstructor
public class SensorDataController {

    private final SensorDataService sensorDataService;

    @PostMapping
    public ResponseDto<?> createSensorData(@RequestBody SensorDataRequestDto sensorDataRequestDto) {
        return ResponseDto.ok(sensorDataService.createSensorData(sensorDataRequestDto));
    }

    @GetMapping("/{drivingSessionId}")
    public ResponseDto<?> getAllSensorDataByDrivingSessionId(@PathVariable Long drivingSessionId) {
        return ResponseDto.ok(sensorDataService.getAllSensorDataByDrivingSessionId(drivingSessionId));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteSensorDataById(@PathVariable Long id) {
        return ResponseDto.ok(sensorDataService.deleteSensorDataById(id));
    }

    @DeleteMapping("/sessionid/{id}")
    public ResponseDto<?> deleteSensorDataBySessionId(@PathVariable Long id) {
        return ResponseDto.ok(sensorDataService.deleteSensorDataByDrivingSessionId(id));
    }

    @GetMapping("/csv/{sessionId}")
    public ResponseEntity<byte[]> getSensorDataAsCsvForSession(@PathVariable Long sessionId) {
        ByteArrayInputStream byteArrayInputStream = sensorDataService.getSensorDataAsCsvForSession(sessionId);

        if (byteArrayInputStream == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=sensordata.csv");
        headers.add("Content-Type", "text/csv");

        try {
            byte[] csvData = byteArrayInputStream.readAllBytes();
            return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
