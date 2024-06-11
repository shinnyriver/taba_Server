package taba.tabaServer.tabaserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taba.tabaServer.tabaserver.domain.Car;
import taba.tabaServer.tabaserver.domain.User;
import taba.tabaServer.tabaserver.dto.cardto.*;
import taba.tabaServer.tabaserver.enums.CarSize;
import taba.tabaServer.tabaserver.exception.CommonException;
import taba.tabaServer.tabaserver.exception.ErrorCode;
import taba.tabaServer.tabaserver.repository.CarRepository;
import taba.tabaServer.tabaserver.repository.UserRepository;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Transactional
    public CarResponseDto createCar(CarDto carDto) {
        User currentUser = userRepository.findById(carDto.userId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        /**
         * 이미지 data는 주고 받을때는 String이지만 받거나 줄땐 각각 decoding,encoding해서 보냄
         */

        Car currentCar = Car.builder()
                .carName(carDto.carName())
                .carNumber(carDto.carNumber())
                .carSize(carDto.carSize())
                .totalDistance(carDto.totalDistance())
                .photo(Base64.getDecoder().decode(carDto.photo()))
                .insurance(carDto.insurance())
                .user(currentUser)
                .purchaseDate(carDto.purchaseDate())
                .drivingScore(100)  //처음 가입시 100점
                .build();

        carRepository.save(currentCar);

        return CarResponseDto.of(
                currentCar.getCarId(),
                currentCar.getCarName(),
                currentCar.getCarSize(),
                currentCar.getTotalDistance(),
                currentCar.getCarNumber(),
                currentCar.getUser().getId(),
                currentCar.getPhoto(),
                currentCar.getInsurance(),
                currentCar.getPurchaseDate(),
                currentCar.getDrivingScore()
        );
    }

    @Transactional
    public CarResponseDto getCarById(Long id) {
        //id로 자동차 정보를 찾고
        Car findCar = carRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CAR));
        //자동차 정보를 반환
        return CarResponseDto.of(
                findCar.getCarId(),
                findCar.getCarName(),
                findCar.getCarSize(),
                findCar.getTotalDistance(),
                findCar.getCarNumber(),
                findCar.getUser().getId(),
                findCar.getPhoto(),
                findCar.getInsurance(),
                findCar.getPurchaseDate(),
                findCar.getDrivingScore()
        );
    }

    @Transactional
    public CarResponseDto updateCar(Long id, CarUpdateDto carUpdateDto) {
        Car findCar = carRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CAR));

        byte[] photoBytes = Base64.getDecoder().decode(carUpdateDto.photo());

        findCar.updateCar(
                carUpdateDto.carId(),
                carUpdateDto.carName(),
                carUpdateDto.carSize(),
                carUpdateDto.totalDistance(),
                carUpdateDto.carNumber(),
                photoBytes,
                carUpdateDto.insurance(),
                carUpdateDto.purchaseDate(),
                carUpdateDto.drivingScore()
        );

        carRepository.save(findCar);

        return CarResponseDto.of(
                findCar.getCarId(),
                findCar.getCarName(),
                findCar.getCarSize(),
                findCar.getTotalDistance(),
                findCar.getCarNumber(),
                findCar.getUser().getId(),
                findCar.getPhoto(),
                findCar.getInsurance(),
                findCar.getPurchaseDate(),
                findCar.getDrivingScore()
        );
    }

    @Transactional
    public Boolean deleteCar(Long id) {
        carRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Transactional
    public List<CarResponseDto> getAllCarByUser(Long userId) {
        return carRepository.findAllByUserId(userId).stream()
                .map(car -> CarResponseDto.of(
                        car.getCarId(),
                        car.getCarName(),
                        car.getCarSize(),
                        car.getTotalDistance(),
                        car.getCarNumber(),
                        car.getUser().getId(),
                        car.getPhoto(),
                        car.getInsurance(),
                        car.getPurchaseDate(),
                        car.getDrivingScore()
                )).collect(Collectors.toList());
    }

    @Transactional
    public List<CarListResponseDto> getCarListForManager() {
        return carRepository.findAll().stream()
                .map(car -> CarListResponseDto.of(
                        car.getCarId(),
                        car.getCarSize(),
                        car.getCarNumber(),
                        car.getUser().getCreatedAt(),
                        car.getUser().getWithdrawAt()
                )).collect(Collectors.toList());
    }

    @Transactional
    public CarResponseDto getCarByCarNumber(String carNumber) {
        Car findCar = carRepository.findCarByCarNumber(carNumber)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CAR));

        return CarResponseDto.of(
                findCar.getCarId(),
                findCar.getCarName(),
                findCar.getCarSize(),
                findCar.getTotalDistance(),
                findCar.getCarNumber(),
                findCar.getUser().getId(),
                findCar.getPhoto(),
                findCar.getInsurance(),
                findCar.getPurchaseDate(),
                findCar.getDrivingScore()
        );
    }

    @Transactional
    public CarSizeDto countCarByCarSize() {
        Long small = carRepository.countByCarSize(CarSize.SMALL);
        Long medium = carRepository.countByCarSize(CarSize.MEDIUM);
        Long large = carRepository.countByCarSize(CarSize.LARGE);

        return CarSizeDto.of(small, medium, large);
    }

    /**
     * 차량 점수 업데이트
     */
    @Transactional
    public CarResponseDto updateCarScore(Long id, CarDrivingScoreUpdateRequestDto drivingScoreRequest) {
        Car findCar = carRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CAR));

        // 드라이빙 스코어 업데이트
        findCar.updateCarScore(drivingScoreRequest.drivingScore());
        // 여기를 수정하여 drivingScore를 DTO에서 가져옴
        carRepository.save(findCar); //점수업데이트 한거 db에 저장
        //저장한 점수 get해서 클라이언트에 응답
        return CarResponseDto.of(
                findCar.getCarId(),
                findCar.getCarName(),
                findCar.getCarSize(),
                findCar.getTotalDistance(),
                findCar.getCarNumber(),
                findCar.getUser().getId(),
                findCar.getPhoto(),
                findCar.getInsurance(),
                findCar.getPurchaseDate(),
                findCar.getDrivingScore()
        );
    }
}
