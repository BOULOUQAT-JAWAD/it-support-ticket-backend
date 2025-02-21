package com.example.ItSupportTicketBackEnd.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {
    private Long userId;
    private String email;
    private String token;
}
