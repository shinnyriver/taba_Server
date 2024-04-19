package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taba.tabaServer.tabaserver.domain.Calibration;
import taba.tabaServer.tabaserver.dto.calibrationdto.CalibrationDto;
import taba.tabaServer.tabaserver.dto.calibrationdto.CalibrationResponseDto;
import taba.tabaServer.tabaserver.repository.CalibrationRepository;

import java.util.stream.Collectors;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CalibrationService {

    private final CalibrationRepository calibrationRepository;

    @Transactional
    public CalibrationResponseDto createCalibration(CalibrationDto calibrationDto){
        Calibration calibration = Calibration.builder()
                .sensorType(calibrationDto.sensorType())
                .pressureMax(calibrationDto.pressureMax())
                .pressureMin(calibrationDto.pressureMin())
                .car(calibrationDto.car())
                .build();

        calibrationRepository.save(calibration);
        return CalibrationResponseDto.of(
                calibration.getId(),
                calibration.getSensorType(),
                calibration.getPressureMax(),
                calibration.getPressureMin(),
                calibration.getCalibrationTime(),
                calibration.getCar()
        );
    }

    @Transactional
    public CalibrationResponseDto getCalibrationById(Long id){
        Calibration calibration = calibrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calibration not found"));
        return CalibrationResponseDto.of(
                calibration.getId(),
                calibration.getSensorType(),
                calibration.getPressureMax(),
                calibration.getPressureMin(),
                calibration.getCalibrationTime(),
                calibration.getCar()
        );
    }

    @Transactional
    public CalibrationResponseDto updateCalibration(Long id, CalibrationDto calibrationDto){
        Calibration calibration = calibrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calibration not found"));
        calibration.updateCalibration(
                calibrationDto.sensorType(),
                calibrationDto.pressureMax(),
                calibrationDto.pressureMin(),
                calibrationDto.car()
        );
        calibrationRepository.save(calibration);
        return CalibrationResponseDto.of(
                calibration.getId(),
                calibration.getSensorType(),
                calibration.getPressureMax(),
                calibration.getPressureMin(),
                calibration.getCalibrationTime(),
                calibration.getCar()
        );
    }

    @Transactional
    public Boolean deleteCalibration(Long id){
        calibrationRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Transactional(readOnly = true)
    public List<CalibrationResponseDto> getAllCalibration() {
        return calibrationRepository.findAll().stream()
                .map(calibration -> CalibrationResponseDto.of(
                        calibration.getId(),
                        calibration.getSensorType(),
                        calibration.getPressureMax(),
                        calibration.getPressureMin(),
                        calibration.getCalibrationTime(),
                        calibration.getCar()
                )).collect(Collectors.toList());
    }
}
