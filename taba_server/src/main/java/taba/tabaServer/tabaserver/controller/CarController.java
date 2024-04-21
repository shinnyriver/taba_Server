package taba.tabaServer.tabaserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public ResponseDto<?> createCar(@RequestBody CarDto carDto){
        return ResponseDto.ok(carService.createCar(carDto));
    }

    @GetMapping("/{id}")
    public ResponseDto<?> getCarById(@PathVariable Long id){
        return ResponseDto.ok(carService.getCarById(id));
    }

    @PutMapping("/{id}")
    public ResponseDto<?> updateCar(@PathVariable Long id, @RequestBody CarUpdateDto carUpdateDto){
        return ResponseDto.ok(carService.updateCar(id,carUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteCar(@PathVariable Long id){
        return ResponseDto.ok(carService.deleteCar(id));
    }

    @GetMapping("/findbyuser/{userId}")
    public ResponseDto<?> getAllCarsByuser(@PathVariable Long userId){
        return ResponseDto.ok(carService.getAllCarByUser(userId));
    }
}
