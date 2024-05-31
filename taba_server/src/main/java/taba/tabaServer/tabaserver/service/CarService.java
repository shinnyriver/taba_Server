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

import java.util.Base64;
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
            /**
             * 이미지 data는 주고 받을때는 String이지만 받거나 줄땐 각각 decoding,encoding해서 보냄
             */
            byte[] photoBytes = Base64.getDecoder().decode(carDto.photo());

            //자동차 정보 입력
            Car currentCar = Car.builder()
                    .carName(carDto.carName())
                    .carNumber(carDto.carNumber())
                    .carSize(carDto.carSize())
                    .totalDistance(carDto.totalDistance())
                    .photo(photoBytes)
                    .insurance(carDto.insurance())
                    .user(currentUser)
                    .purchaseDate(carDto.purchaseDate())
                    .drivingScore(carDto.drivingScore())
                    .build();
            //저장
            carRepository.save(currentCar);
            //저장 내용 반환
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
        public CarResponseDto getCarById(Long id){
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
        public CarResponseDto updateCar(Long id, CarUpdateDto carUpdateDto){
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
                    carUpdateDto.purchaseDate()

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
        public Boolean deleteCar(Long id){
            carRepository.deleteById(id);
            return Boolean.TRUE;
        }

        @Transactional
        public List<CarResponseDto> getAllCarByUser(Long userId){
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

}
