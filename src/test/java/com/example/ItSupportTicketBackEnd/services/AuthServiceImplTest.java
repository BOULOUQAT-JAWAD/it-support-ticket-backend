package com.example.ItSupportTicketBackEnd.services;

import com.example.ItSupportTicketBackEnd.core.enums.Role;
import com.example.ItSupportTicketBackEnd.core.exceptions.GlobalException;
import com.example.ItSupportTicketBackEnd.dtos.requests.AuthRequestDTO;
import com.example.ItSupportTicketBackEnd.dtos.responses.AuthResponseDTO;
import com.example.ItSupportTicketBackEnd.entities.User;
import com.example.ItSupportTicketBackEnd.services.implementations.AuthServiceImpl;
import com.example.ItSupportTicketBackEnd.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthServiceImpl authService;

    private AuthRequestDTO authRequest;
    private UserDetails userDetails;
    private User userEntity;

    @BeforeEach
    void setUp() {
        authRequest = new AuthRequestDTO();
        authRequest.setEmail("john.doe@example.com");
        authRequest.setPassword("Employee123");

        userDetails = new org.springframework.security.core.userdetails.User(
                "john.doe@example.com", 
                "$2a$10$K5Qz5Qz5Qz5Qz5Qz5Qz5Q.t5u7w9x1y3z5A7B9C1D3E5F7G9H1I3J", 
                true, true, true, true, 
                java.util.Collections.emptyList()
        );

        userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("john.doe@example.com");
        userEntity.setPassword("$2a$10$K5Qz5Qz5Qz5Qz5Qz5Qz5Q.t5u7w9x1y3z5A7B9C1D3E5F7G9H1I3J");
        userEntity.setRole(Role.EMPLOYEE);
    }

    @Test
    void testAuthenticateUser_Success() {
        // Arrange:
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userService.getByEmail("john.doe@example.com")).thenReturn(userEntity);
        when(jwtUtils.generateToken(userDetails)).thenReturn("mocked-jwt-token");

        // Act
        AuthResponseDTO response = authService.authenticateUser(authRequest);

        // Assert
        assertNotNull(response);
        assertEquals("john.doe@example.com", response.getEmail());
        assertEquals(1L, response.getUserId());
        assertEquals("mocked-jwt-token", response.getToken());

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService).getByEmail("john.doe@example.com");
        verify(jwtUtils).generateToken(userDetails);
    }

    @Test
    void testAuthenticateUser_AuthenticationFailure() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new org.springframework.security.core.AuthenticationException("Invalid credentials") {});

        // Act & Assert
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            authService.authenticateUser(authRequest);
        });

        assertEquals("Invalid credentials", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(userService, jwtUtils);
    }
}