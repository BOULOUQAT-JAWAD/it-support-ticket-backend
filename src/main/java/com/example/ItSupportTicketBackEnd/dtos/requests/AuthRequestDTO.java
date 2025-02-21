package com.example.ItSupportTicketBackEnd.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRequestDTO {

    @NotEmpty(message = "Email is required.")
    @Email(message = "Email is Invalid.")
    private String email;

    @NotEmpty(message = "Password is required.")
    private String password;
}
