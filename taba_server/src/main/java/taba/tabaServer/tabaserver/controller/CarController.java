package taba.tabaServer.tabaserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import taba.tabaServer.tabaserver.dto.cardto.CarDrivingScoreUpdateRequestDto;
import taba.tabaServer.tabaserver.dto.cardto.CarDto;
import taba.tabaServer.tabaserver.dto.cardto.CarUpdateDto;
import taba.tabaServer.tabaserver.dto.global.ResponseDto;
import taba.tabaServer.tabaserver.service.CarService;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseDto<?> createCar(@RequestBody CarDto carDto) {
        return ResponseDto.ok(carService.createCar(carDto));
    }

    @GetMapping("/{id}")
    public ResponseDto<?> getCarById(@PathVariable Long id) {
        return ResponseDto.ok(carService.getCarById(id));
    }

    @PutMapping("/{id}")
    public ResponseDto<?> updateCar(@PathVariable Long id, @RequestBody CarUpdateDto carUpdateDto) {
        return ResponseDto.ok(carService.updateCar(id, carUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteCar(@PathVariable Long id) {
        return ResponseDto.ok(carService.deleteCar(id));
    }

    //사용자 id로 조회
    @GetMapping("/user/{userId}")
    public ResponseDto<?> getAllCarsByuser(@PathVariable Long userId) {
        return ResponseDto.ok(carService.getAllCarByUser(userId));
    }

    @GetMapping("/manager/list")
    public ResponseDto<?> getAllCarsForManager() {
        return ResponseDto.ok(carService.getCarListForManager());
    }

    @GetMapping("/carnumber/{carNumber}")
    public ResponseDto<?> getCarByCarNumber(@PathVariable String carNumber) {
        return ResponseDto.ok(carService.getCarByCarNumber(carNumber));
    }

    @GetMapping("/statistics/carsize")
    public ResponseDto<?> getStatisticsByCarSize() {
        return ResponseDto.ok(carService.countCarByCarSize());
    }

    @PutMapping("score/{id}")
    public ResponseDto<?> updateCarScore(@PathVariable Long id, @RequestBody CarDrivingScoreUpdateRequestDto drivingScoreRequest) {
        return ResponseDto.ok(carService.updateCarScore(id, drivingScoreRequest));
    }
}
