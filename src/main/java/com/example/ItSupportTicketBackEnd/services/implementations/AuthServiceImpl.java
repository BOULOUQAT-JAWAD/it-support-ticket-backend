package com.example.ItSupportTicketBackEnd.services.implementations;

import com.example.ItSupportTicketBackEnd.core.exceptions.GlobalException;
import com.example.ItSupportTicketBackEnd.dtos.requests.AuthRequestDTO;
import com.example.ItSupportTicketBackEnd.dtos.responses.AuthResponseDTO;
import com.example.ItSupportTicketBackEnd.entities.User;
import com.example.ItSupportTicketBackEnd.services.AuthService;
import com.example.ItSupportTicketBackEnd.services.UserService;
import com.example.ItSupportTicketBackEnd.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Override
    public AuthResponseDTO authenticateUser(@Valid AuthRequestDTO request) {
        try {
            log.info("Authentication request has been validated.");
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()));

            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            log.info("User with email {} is authenticated ", user.getUsername());
            User userInfo = userService.getByEmail(user.getUsername());
            return AuthResponseDTO.builder()
                    .email(user.getUsername())
                    .userId(userInfo.getId())
                    .token(jwtUtils.generateToken(user))
                    .build();
        } catch (AuthenticationException e) {
            log.error("Couldn't authenticate the user : ",e);
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public User getAuthenticatedUser() {
        org.springframework.security.core.userdetails.User
                user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getByEmail(user.getUsername());
    }
}
