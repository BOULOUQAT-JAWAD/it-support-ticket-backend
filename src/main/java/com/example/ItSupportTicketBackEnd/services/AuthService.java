package com.example.ItSupportTicketBackEnd.services;


import com.example.ItSupportTicketBackEnd.dtos.requests.AuthRequestDTO;
import com.example.ItSupportTicketBackEnd.dtos.responses.AuthResponseDTO;
import com.example.ItSupportTicketBackEnd.entities.User;

public interface AuthService {
    AuthResponseDTO authenticateUser(AuthRequestDTO authRequestDTO);

    User getAuthenticatedUser();

}
