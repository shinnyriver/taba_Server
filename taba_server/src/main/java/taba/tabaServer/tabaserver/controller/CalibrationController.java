package taba.tabaServer.tabaserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import taba.tabaServer.tabaserver.dto.calibrationdto.CalibrationDto;
import taba.tabaServer.tabaserver.dto.global.ResponseDto;
import taba.tabaServer.tabaserver.service.CalibrationService;

@RestController
@RequestMapping("/api/calibrations")
@RequiredArgsConstructor
public class CalibrationController {

    private final CalibrationService calibrationService;

    @PostMapping
    public ResponseDto<?> createCalibration(@RequestBody CalibrationDto calibrationDto) {
        return ResponseDto.ok(calibrationService.createCalibration(calibrationDto));
    }

    @GetMapping("/{id}")
    public ResponseDto<?> getCalibrationById(@PathVariable Long id) {
        return ResponseDto.ok(calibrationService.getCalibrationById(id));
    }

    @PutMapping("/{id}")
    public ResponseDto<?> updateCalibration(@PathVariable Long id, @RequestBody CalibrationDto calibrationDto){
        return ResponseDto.ok(calibrationService.updateCalibration(id,calibrationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteCalibration(@PathVariable Long id){
        return ResponseDto.ok(calibrationService.deleteCalibration(id));
    }

    @GetMapping("/findbycarid/{id}")
    public ResponseDto<?> getAllCalibrationsByCarId(@PathVariable Long id) {
        return ResponseDto.ok(calibrationService.getAllCalibration(id));
    }
}
