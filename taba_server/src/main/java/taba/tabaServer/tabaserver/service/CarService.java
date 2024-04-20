package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taba.tabaServer.tabaserver.domain.Car;
import taba.tabaServer.tabaserver.domain.User;
import taba.tabaServer.tabaserver.dto.cardto.CarDto;
import taba.tabaServer.tabaserver.dto.cardto.CarResponseDto;
import taba.tabaServer.tabaserver.dto.cardto.CarUpdateDto;
import taba.tabaServer.tabaserver.exception.CommonException;
import taba.tabaServer.tabaserver.exception.ErrorCode;
import taba.tabaServer.tabaserver.repository.CarRepository;
import taba.tabaServer.tabaserver.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Transactional
    public CarResponseDto createCar(CarDto carDto){
        User currentUser = userRepository.findById(carDto.userId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        Car currentCar = Car.builder()
                .carName(carDto.carName())
                .carNumber(carDto.carNumber())
                .carSize(carDto.carSize())
                .totalDistance(carDto.totalDistance())
                .user(currentUser)
                .build();

        carRepository.save(currentCar);
        return CarResponseDto.of(
                currentCar.getCarName(),
                currentCar.getCarSize(),
                currentCar.getTotalDistance(),
                currentCar.getCarNumber(),
                currentCar.getUser().getId()
        );
    }

    @Transactional
    public CarResponseDto getCarById(Long id){
        Car findCar = carRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CAR));
        return CarResponseDto.of(
                findCar.getCarName(),
                findCar.getCarSize(),
                findCar.getTotalDistance(),
                findCar.getCarNumber(),
                findCar.getUser().getId()
        );
    }

    @Transactional
    public CarResponseDto updateCar(Long id, CarUpdateDto carUpdateDto){
        Car findCar = carRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CAR));

        User findUser = userRepository.findById(findCar.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        findCar.updateCar(
                carUpdateDto.carName(),
                carUpdateDto.carSize(),
                carUpdateDto.totalDistance(),
                carUpdateDto.carNumber()
        );

        carRepository.save(findCar);

        return CarResponseDto.of(
                findCar.getCarName(),
                findCar.getCarSize(),
                findCar.getTotalDistance(),
                findCar.getCarNumber(),
                findCar.getUser().getId()
        );
    }

    @Transactional
    public Boolean deleteCar(Long id){
        carRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Transactional
    public List<CarResponseDto> getAllCarByUser(Long userId){
        return carRepository.findAllByUserId(userId).stream()
                .map(car -> CarResponseDto.of(
                        car.getCarName(),
                        car.getCarSize(),
                        car.getTotalDistance(),
                        car.getCarNumber(),
                        car.getUser().getId()
                )).collect(Collectors.toList());
    }

}
