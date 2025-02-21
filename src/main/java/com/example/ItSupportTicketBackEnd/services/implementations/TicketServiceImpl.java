package com.example.ItSupportTicketBackEnd.services.implementations;

import com.example.ItSupportTicketBackEnd.core.enums.TrackingStatus;
import com.example.ItSupportTicketBackEnd.dtos.requests.TicketRequestDTO;
import com.example.ItSupportTicketBackEnd.dtos.responses.TicketResponseDTO;
import com.example.ItSupportTicketBackEnd.services.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Override
    public TicketResponseDTO persist(TicketRequestDTO request) {
        return null;
    }

    @Override
    public TicketResponseDTO updateStatus(TrackingStatus trackingStatus) {
        return null;
    }

    @Override
    public List<TicketResponseDTO> getUserTickets(Long id) {
        return null;
    }

    @Override
    public List<TicketResponseDTO> getAllTickets() {
        return null;
    }
}
