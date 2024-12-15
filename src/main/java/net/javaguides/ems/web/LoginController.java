package net.javaguides.ems.web;

import lombok.AllArgsConstructor;
import net.javaguides.ems.models.User;
import net.javaguides.ems.models.dto.LoginRequest;
import net.javaguides.ems.models.exceptions.InvalidArgumentsException;
import net.javaguides.ems.models.exceptions.InvalidUserCredentialsException;
import net.javaguides.ems.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(user.getUsername());
        } catch (InvalidUserCredentialsException | InvalidArgumentsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

}
