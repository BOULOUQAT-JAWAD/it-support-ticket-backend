package com.example.ItSupportTicketBackEnd.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DefaultResponseDTO {
    private String status;
    private String message;
    private Date time;
}