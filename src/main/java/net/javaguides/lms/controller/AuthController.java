package net.javaguides.lms.controller;

import jakarta.validation.Valid;
import net.javaguides.lms.dto.AuthResponse;
import net.javaguides.lms.dto.LoginRequest;
import net.javaguides.lms.dto.SignupRequest;
import net.javaguides.lms.entity.User;
import net.javaguides.lms.service.AuthService;
import net.javaguides.lms.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        try {
            User user = authService.signup(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponse(
                            "User registered successfully",
                            user.getEmail(),
                            user.getRole().toString(),
                            null
                    ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(e.getMessage(), null, null, null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = authService.authenticate(request.getEmail(), request.getPassword());
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().toString());
            
            return ResponseEntity.ok(new AuthResponse(
                    "Login successful",
                    user.getEmail(),
                    user.getRole().toString(),
                    token
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(e.getMessage(), null, null, null));
        }
    }
}