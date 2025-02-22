package com.example.ItSupportTicketBackEnd.dtos.requests;

import jakarta.validation.constraints.NotNull;

public class CommentRequestDTO {

    @NotNull(message = "ticketId is required")
    private Long ticketId;

    @NotNull(message = "content is required")
    private String content;
}
