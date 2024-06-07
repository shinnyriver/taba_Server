package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taba.tabaServer.tabaserver.domain.Calibration;
import taba.tabaServer.tabaserver.domain.Car;
import taba.tabaServer.tabaserver.dto.calibrationdto.CalibrationDto;
import taba.tabaServer.tabaserver.dto.calibrationdto.CalibrationResponseDto;
import taba.tabaServer.tabaserver.exception.CommonException;
import taba.tabaServer.tabaserver.exception.ErrorCode;
import taba.tabaServer.tabaserver.repository.CalibrationRepository;
import taba.tabaServer.tabaserver.repository.CarRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CalibrationService {

    private final CalibrationRepository calibrationRepository;
    private final CarRepository carRepository;

    @Transactional
    public CalibrationResponseDto createCalibration(CalibrationDto calibrationDto) {
        Car currentCar = carRepository.findById(calibrationDto.carId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CAR)); //나중에 추가
        Calibration calibration = Calibration.builder()
                .sensorType(calibrationDto.sensorType())
                .pressureMax(calibrationDto.pressureMax())
                .pressureMin(calibrationDto.pressureMin())
                .car(currentCar)
                .build();

        calibrationRepository.save(calibration);
        return CalibrationResponseDto.of(
                calibration.getId(),
                calibration.getSensorType(),
                calibration.getPressureMax(),
                calibration.getPressureMin(),
                calibration.getCalibrationTime(),
                calibration.getCar().getCarId()
        );
    }

    @Transactional
    public CalibrationResponseDto getCalibrationById(Long id) {
        Calibration calibration = calibrationRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CALIBRATION));
        return CalibrationResponseDto.of(
                calibration.getId(),
                calibration.getSensorType(),
                calibration.getPressureMax(),
                calibration.getPressureMin(),
                calibration.getCalibrationTime(),
                calibration.getCar().getCarId()
        );
    }

    @Transactional
    public CalibrationResponseDto updateCalibration(Long id, CalibrationDto calibrationDto) {
        Calibration calibration = calibrationRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CALIBRATION));

        Car currentCar = carRepository.findById(calibrationDto.carId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CAR));
        calibration.updateCalibration(
                calibrationDto.sensorType(),
                calibrationDto.pressureMax(),
                calibrationDto.pressureMin(),
                currentCar
        );

        calibrationRepository.save(calibration);
        return CalibrationResponseDto.of(
                calibration.getId(),
                calibration.getSensorType(),
                calibration.getPressureMax(),
                calibration.getPressureMin(),
                calibration.getCalibrationTime(),
                calibration.getCar().getCarId()
        );
    }

    @Transactional
    public Boolean deleteCalibration(Long id) {
        calibrationRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Transactional(readOnly = true)
    public List<CalibrationResponseDto> getAllCalibrationById(Long id) {
        return calibrationRepository.findAllByCar_CarId(id).stream()
                .map(calibration -> CalibrationResponseDto.of(
                        calibration.getId(),
                        calibration.getSensorType(),
                        calibration.getPressureMax(),
                        calibration.getPressureMin(),
                        calibration.getCalibrationTime(),
                        calibration.getCar().getCarId()
                )).collect(Collectors.toList());
    }

    //차량 이름으로 캘리브레이션 조회하기
    @Transactional(readOnly = true)
    public List<CalibrationResponseDto> getAllCalibrationByName(String carName) {
        return calibrationRepository.findAllByCar_CarName(carName).stream()
                .map(calibration -> CalibrationResponseDto.of(
                        calibration.getId(),
                        calibration.getSensorType(),
                        calibration.getPressureMax(),
                        calibration.getPressureMin(),
                        calibration.getCalibrationTime(),
                        calibration.getCar().getCarId()
                )).collect(Collectors.toList());
    }
}
