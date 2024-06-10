package taba.tabaServer.tabaserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.DrivingSessionErrorOccuredDto;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.DrivingSessionRequestDto;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.DrivingSessionUpdateDto;
import taba.tabaServer.tabaserver.dto.global.ResponseDto;
import taba.tabaServer.tabaserver.enums.ErrorStatus;
import taba.tabaServer.tabaserver.service.DrivingSessionService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/drivingsessions")
@RequiredArgsConstructor
public class DrivingSessionController {

    private final DrivingSessionService drivingSessionService;

    @PostMapping
    public ResponseDto<?> createDrivingSession(@RequestBody DrivingSessionRequestDto drivingSessionRequestDto) {
        return ResponseDto.ok(drivingSessionService.createDrivingSession(drivingSessionRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseDto<?> getDrivingSessionById(@PathVariable Long id) {
        return ResponseDto.ok(drivingSessionService.getDrivingSessionById(id));
    }

    @PutMapping("/end/{id}")
    public ResponseDto<?> endDrivingSession(@PathVariable Long id, @RequestBody DrivingSessionUpdateDto drivingSessionUpdateDto) {
        return ResponseDto.ok(drivingSessionService.endDrivingSession(id, drivingSessionUpdateDto));
    }

    @PutMapping("/error/{id}")
    public ResponseDto<?> drivingSessionErrorOccured(@PathVariable Long id, @RequestBody DrivingSessionErrorOccuredDto drivingSessionErrorOccuredDto) {
        return ResponseDto.ok(drivingSessionService.drivingSessionErrorOccured(id, drivingSessionErrorOccuredDto));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteDrivingSession(@PathVariable Long id) {
        return ResponseDto.ok(drivingSessionService.deleteDrivingSession(id));
    }

    @GetMapping("/findbyuser/{userId}")
    public ResponseDto<?> getAllDrivingSessionByUser(@PathVariable Long userId) {
        return ResponseDto.ok(drivingSessionService.getAllDrivingSessionByUserId(userId));
    }

    @GetMapping("/errorstats/{errorStatus}")
    public ResponseDto<?> getDrivingSessionsByErrorStatus(@PathVariable ErrorStatus errorStatus) {
        return ResponseDto.ok(drivingSessionService.findAllByErrorStatus(errorStatus));
    }

    @GetMapping("/search")  //usage : /api/drivingsessions/search?startDate=2024-05-01&endDate=2024-05-08
    public ResponseDto<?> getSessionsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseDto.ok(drivingSessionService.getSessionsBetweenDates(startDate, endDate));
    }

    @GetMapping("error/list")
    public ResponseDto<?> getErrorList() {
        return ResponseDto.ok(drivingSessionService.getErrorList());
    }

    @PutMapping("solve/{id}")
    public ResponseDto<?> postSolve(@PathVariable Long id) {
        return ResponseDto.ok(drivingSessionService.solveProblem(id));
    }

    @GetMapping("findbycar/{carNumber}")
    public ResponseDto<?> getDrivingSessionsByCarNumber(@PathVariable String carNumber){
        return ResponseDto.ok(drivingSessionService.getListByCarNumber(carNumber));
    }
}
