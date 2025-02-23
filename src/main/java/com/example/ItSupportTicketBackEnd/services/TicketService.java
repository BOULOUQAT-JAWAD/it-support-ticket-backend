package com.example.ItSupportTicketBackEnd.services;


import com.example.ItSupportTicketBackEnd.core.enums.TrackingStatus;
import com.example.ItSupportTicketBackEnd.dtos.requests.TicketRequestDTO;
import com.example.ItSupportTicketBackEnd.dtos.responses.TicketResponseDTO;
import com.example.ItSupportTicketBackEnd.entities.Ticket;

import java.util.List;

public interface TicketService {
    TicketResponseDTO persist(TicketRequestDTO request);
    TicketResponseDTO updateStatus(long id, String trackingStatus);
    List<TicketResponseDTO> getUserTickets(Long ticketId, TrackingStatus status);
    List<TicketResponseDTO> getAllTickets(Long ticketId, TrackingStatus status);
    Ticket geTicket(long id);
}
