package com.example.ItSupportTicketBackEnd.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDTO {
    private long id;
    private UserResponseDTO user;
    private String content;
    private LocalDateTime createdAt;
}
