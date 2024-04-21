package taba.tabaServer.tabaserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import taba.tabaServer.tabaserver.dto.global.ResponseDto;
import taba.tabaServer.tabaserver.dto.sensordatadto.SensorDataRequestDto;
import taba.tabaServer.tabaserver.service.SensorDataService;

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



}
