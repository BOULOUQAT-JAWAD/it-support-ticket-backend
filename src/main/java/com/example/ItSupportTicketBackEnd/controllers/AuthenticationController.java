package com.example.ItSupportTicketBackEnd.controllers;

import com.example.ItSupportTicketBackEnd.dtos.requests.AuthRequestDTO;
import com.example.ItSupportTicketBackEnd.dtos.responses.AuthResponseDTO;
import com.example.ItSupportTicketBackEnd.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDto) {
        return new ResponseEntity<>(authService.authenticateUser(authRequestDto), HttpStatus.OK);
    }

}
