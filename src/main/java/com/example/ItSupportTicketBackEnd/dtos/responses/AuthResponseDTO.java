package com.example.ItSupportTicketBackEnd.dtos.responses;

import com.example.ItSupportTicketBackEnd.core.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {
    private Long userId;
    private String email;
    private Role role;
    private String token;
}
