package taba.tabaServer.tabaserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taba.tabaServer.tabaserver.dto.global.ResponseDto;
import taba.tabaServer.tabaserver.dto.userdto.CreateUserDto;
import taba.tabaServer.tabaserver.dto.userdto.UpdateUserDto;
import taba.tabaServer.tabaserver.dto.userdto.UserResponseDto;
import taba.tabaServer.tabaserver.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseDto<?> createUser(@RequestBody CreateUserDto dto) {
        return ResponseDto.ok(userService.createUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseDto<?> getUserById(@PathVariable Long id) {
        return ResponseDto.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseDto<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto dto) {
        return ResponseDto.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> deleteUser(@PathVariable Long id) {
        return ResponseDto.ok(userService.deleteUser(id));
    }

    @GetMapping
    public ResponseDto<?> getAllUsers() {
        return ResponseDto.ok(userService.getAllUsers());
    }
}
