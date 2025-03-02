package com.user.subscriptions.apiuser.controllers;

import com.user.subscriptions.apiuser.dto.UserDto;
import com.user.subscriptions.apiuser.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    @Operation(summary = "Создание пользователя")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск по id пользователя")
    public ResponseEntity<UserDto> getUserById(@Valid @PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));

    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление по id пользователя")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление по id пользователя")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
