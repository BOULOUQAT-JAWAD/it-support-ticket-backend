package com.example.ItSupportTicketBackEnd.services.implementations;

import com.example.ItSupportTicketBackEnd.core.enums.TicketActionType;
import com.example.ItSupportTicketBackEnd.core.enums.TrackingStatus;
import com.example.ItSupportTicketBackEnd.core.exceptions.DataNotFoundException;
import com.example.ItSupportTicketBackEnd.dtos.requests.TicketRequestDTO;
import com.example.ItSupportTicketBackEnd.dtos.responses.TicketResponseDTO;
import com.example.ItSupportTicketBackEnd.entities.AuditLog;
import com.example.ItSupportTicketBackEnd.entities.Ticket;
import com.example.ItSupportTicketBackEnd.mappers.TicketMapper;
import com.example.ItSupportTicketBackEnd.repositories.AuditLogRepository;
import com.example.ItSupportTicketBackEnd.repositories.TicketRepository;
import com.example.ItSupportTicketBackEnd.services.AuthService;
import com.example.ItSupportTicketBackEnd.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final AuthService authService;
    private final AuditLogRepository auditLogRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper, AuthService authService, AuditLogRepository auditLogRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.authService = authService;
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public TicketResponseDTO persist(@Valid TicketRequestDTO request) {
        Ticket ticket = ticketMapper.toEntity(request);
        ticket.setCreatedBy(
                authService.getAuthenticatedUser()
        );
        return ticketMapper.toDTO(ticketRepository.save(ticket));
    }

    @Transactional
    @Override
    public TicketResponseDTO updateStatus(long id, String trackingStatus) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if(optionalTicket.isPresent()){

            Ticket ticket = optionalTicket.get();
            ticket.setStatus(TrackingStatus.valueOf(trackingStatus));
            Ticket newTicket = ticketRepository.save(ticket);

            auditLogRepository.save(
                    AuditLog.builder()
                            .ticket(newTicket)
                            .user(authService.getAuthenticatedUser())
                            .actionType(TicketActionType.STATUS_CHANGE)
                            .details(newTicket.getCreatedBy().getEmail() + " change status from "+ticket.getStatus()+" to "+newTicket.getStatus())
                            .build()
            );

            return ticketMapper.toDTO(newTicket);
        }
        throw new DataNotFoundException("Ticket Not Found");
    }

    @Override
    public List<TicketResponseDTO> getUserTickets(Long ticketId, TrackingStatus status) {

        return ticketRepository.findUserTickets(
              authService.getAuthenticatedUser().getId(), ticketId, status
        ).stream().map(
              (ticketMapper::toDTO)
        ).toList();
    }

    @Override
    public List<TicketResponseDTO> getAllTickets(Long ticketId, TrackingStatus status) {
        List<Ticket> tickets = ticketRepository.findFilteredTickets(ticketId, status);
        return tickets.stream().map(
                (ticketMapper::toDTO)
        ).toList();
    }

    @Override
    public Ticket geTicket(long id) {
        return ticketRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Ticket with id "+id+" not found")
        );
    }

    @Override
    public TicketResponseDTO getOneTicket(long ticketId) {
        return ticketMapper.toDTO(geTicket(ticketId));
    }

}
