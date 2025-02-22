package com.example.ItSupportTicketBackEnd.services;


import com.example.ItSupportTicketBackEnd.entities.User;

public interface UserService {
    User getByEmail(String email);
}
