package com.example.ItSupportTicketBackEnd.services;


import com.example.ItSupportTicketBackEnd.core.enums.TrackingStatus;
import com.example.ItSupportTicketBackEnd.dtos.requests.TicketRequestDTO;
import com.example.ItSupportTicketBackEnd.dtos.responses.TicketResponseDTO;

import java.util.List;

public interface TicketService {
    TicketResponseDTO persist(TicketRequestDTO request);
    TicketResponseDTO updateStatus(TrackingStatus trackingStatus);
    List<TicketResponseDTO> getUserTickets(Long id);
    List<TicketResponseDTO> getAllTickets();
}
