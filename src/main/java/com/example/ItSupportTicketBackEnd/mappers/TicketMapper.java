package com.example.ItSupportTicketBackEnd.mappers;

import com.example.ItSupportTicketBackEnd.dtos.requests.TicketRequestDTO;
import com.example.ItSupportTicketBackEnd.dtos.responses.TicketResponseDTO;
import com.example.ItSupportTicketBackEnd.entities.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    Ticket toEntity(TicketRequestDTO request);
    TicketResponseDTO toDTO(Ticket ticket);
}
