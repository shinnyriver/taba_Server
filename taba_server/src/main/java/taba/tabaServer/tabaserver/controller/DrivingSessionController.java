package taba.tabaServer.tabaserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.DrivingSessionErrorOccuredDto;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.DrivingSessionRequestDto;
import taba.tabaServer.tabaserver.dto.drivingsessiondto.DrivingSessionUpdateDto;
import taba.tabaServer.tabaserver.dto.global.ResponseDto;
import taba.tabaServer.tabaserver.service.DrivingSessionService;

@RestController
@RequestMapping("/api/drivingsessions")
@RequiredArgsConstructor
public class DrivingSessionController {

    private final DrivingSessionService drivingSessionService;

    @PostMapping
    public ResponseDto<?> createDrivingSession(@RequestBody DrivingSessionRequestDto drivingSessionRequestDto){
        return ResponseDto.ok(drivingSessionService.createDrivingSession(drivingSessionRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseDto<?> getDrivingSessionById(@PathVariable Long id){
        return ResponseDto.ok(drivingSessionService.getDrivingSessionById(id));
    }

    @PutMapping("/end/{id}")
    public ResponseDto<?> endDrivingSession(@PathVariable Long id, @RequestBody DrivingSessionUpdateDto drivingSessionUpdateDto){
        return ResponseDto.ok(drivingSessionService.endDrivingSession(id, drivingSessionUpdateDto));
    }

    @PutMapping("/error/{id}")
    public ResponseDto<?> drivingSessionErrorOccured(@PathVariable Long id, @RequestBody DrivingSessionErrorOccuredDto drivingSessionErrorOccuredDto){
        return ResponseDto.ok(drivingSessionService.drivingSessionErrorOccured(id,drivingSessionErrorOccuredDto));
    }

    @DeleteMapping("{/id}")
    public ResponseDto<?> deleteDrivingSession(@PathVariable Long id){
        return ResponseDto.ok(drivingSessionService.deleteDrivingSession(id));
    }

    @GetMapping("/findbyuser/{userId}")
    public ResponseDto<?> getAllDrivingSessionByUser(@PathVariable Long userId){
        return ResponseDto.ok(drivingSessionService.getAllDrivingSessionByUserId(userId));
    }
}
