package com.example.ItSupportTicketBackEnd.services;

import com.example.ItSupportTicketBackEnd.dtos.requests.CommentRequestDTO;

public interface CommentService {
    void persist(long ticketId, CommentRequestDTO request);
}
