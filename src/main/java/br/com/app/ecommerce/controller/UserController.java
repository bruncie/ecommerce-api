package br.com.app.ecommerce.controller;

import br.com.app.ecommerce.dto.UpdateUserDto;
import br.com.app.ecommerce.dto.UserRequestDto;
import br.com.app.ecommerce.dto.UserResponseDto;
import br.com.app.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userService.createUser(userRequestDto), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                     @Valid @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(userService.updateUser(id, updateUserDto));
    }
    
    @GetMapping("/{userName}")
    public ResponseEntity<UserResponseDto> findByUserName(@PathVariable String userName) {
        return ResponseEntity.ok(userService.findByUserName(userName));
    }
}