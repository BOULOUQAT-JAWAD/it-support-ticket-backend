package com.example.ItSupportTicketBackEnd.dtos.requests;

import com.example.ItSupportTicketBackEnd.core.enums.Category;
import com.example.ItSupportTicketBackEnd.core.enums.Priority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketRequestDTO {

    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    private String title;

    @NotNull(message = "description is required")
    @NotEmpty(message = "description is required")
    private String description;

    @NotNull(message = "priority is required")
    private Priority priority;

    @NotNull(message = "category is required")
    private Category category;
}
