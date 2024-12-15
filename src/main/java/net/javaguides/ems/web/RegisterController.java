package net.javaguides.ems.web;

import lombok.RequiredArgsConstructor;
import net.javaguides.ems.models.dto.UserDto;
import net.javaguides.ems.models.exceptions.InvalidArgumentsException;
import net.javaguides.ems.models.exceptions.UsernameAlreadyExistsException;
import net.javaguides.ems.service.AuthService;
import net.javaguides.ems.service.UserService;
import net.javaguides.ems.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            this.userService.register(userDto);
            return ResponseEntity.ok("Registration successful. Please login.");
        } catch (InvalidArgumentsException | UsernameAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
