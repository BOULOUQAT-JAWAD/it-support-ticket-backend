package com.example.ItSupportTicketBackEnd.dtos.responses;

import com.example.ItSupportTicketBackEnd.core.enums.Category;
import com.example.ItSupportTicketBackEnd.core.enums.Priority;
import com.example.ItSupportTicketBackEnd.core.enums.TrackingStatus;
import com.example.ItSupportTicketBackEnd.entities.Comment;
import com.example.ItSupportTicketBackEnd.entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketResponseDTO {
    private long id;
    private String title;
    private String description;
    private Priority priority;
    private Category category;
    private TrackingStatus status;
    private List<CommentResponseDTO> comments;
    private UserResponseDTO createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
