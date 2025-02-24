package com.example.ItSupportTicketBackEnd.services;

import com.example.ItSupportTicketBackEnd.core.enums.Category;
import com.example.ItSupportTicketBackEnd.core.enums.Priority;
import com.example.ItSupportTicketBackEnd.core.enums.Role;
import com.example.ItSupportTicketBackEnd.core.enums.TrackingStatus;
import com.example.ItSupportTicketBackEnd.core.exceptions.DataNotFoundException;
import com.example.ItSupportTicketBackEnd.dtos.requests.TicketRequestDTO;
import com.example.ItSupportTicketBackEnd.dtos.responses.TicketResponseDTO;
import com.example.ItSupportTicketBackEnd.entities.Ticket;
import com.example.ItSupportTicketBackEnd.entities.User;
import com.example.ItSupportTicketBackEnd.mappers.TicketMapper;
import com.example.ItSupportTicketBackEnd.repositories.AuditLogRepository;
import com.example.ItSupportTicketBackEnd.repositories.TicketRepository;
import com.example.ItSupportTicketBackEnd.services.implementations.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @Mock
    private AuthService authService;

    @Mock
    private AuditLogRepository auditLogRepository;
    @InjectMocks
    private TicketServiceImpl ticketService;

    private User authenticatedUser;
    private Ticket ticket;
    private TicketRequestDTO ticketRequest;
    private TicketResponseDTO ticketResponse;

    @BeforeEach
    void setUp() {
        authenticatedUser = new User();
        authenticatedUser.setId(1L);
        authenticatedUser.setEmail("john.doe@example.com");
        authenticatedUser.setRole(Role.EMPLOYEE);

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitle("Server Outage");
        ticket.setDescription("Main server down");
        ticket.setPriority(Priority.HIGH);
        ticket.setCategory(Category.NETWORK);
        ticket.setStatus(TrackingStatus.NEW);
        ticket.setCreatedBy(authenticatedUser);

        ticketRequest = new TicketRequestDTO();
        ticketRequest.setTitle("Server Outage");
        ticketRequest.setDescription("Main server down");
        ticketRequest.setPriority(Priority.HIGH);
        ticketRequest.setCategory(Category.NETWORK);

        ticketResponse = new TicketResponseDTO();
        ticketResponse.setId(1L);
        ticketResponse.setTitle("Server Outage");
        ticketResponse.setStatus(TrackingStatus.NEW);
    }

    @Test
    void testPersist_Success() {
        // Arrange
        when(authService.getAuthenticatedUser()).thenReturn(authenticatedUser);
        when(ticketMapper.toEntity(ticketRequest)).thenReturn(ticket);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(ticketMapper.toDTO(ticket)).thenReturn(ticketResponse);

        // Act
        TicketResponseDTO result = ticketService.persist(ticketRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Server Outage", result.getTitle());
        assertEquals(TrackingStatus.NEW, result.getStatus());
        verify(authService).getAuthenticatedUser();
        verify(ticketMapper).toEntity(ticketRequest);
        verify(ticketRepository).save(ticket);
        verify(ticketMapper).toDTO(ticket);
    }

    @Test
    void testUpdateStatus_Success() {
        ticketResponse.setStatus(TrackingStatus.IN_PROGRESS);

        // Arrange
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(auditLogRepository.save(any())).thenReturn(null);
        when(ticketMapper.toDTO(ticket)).thenReturn(ticketResponse);

        // Act
        TicketResponseDTO result = ticketService.updateStatus(1L, "IN_PROGRESS");

        // Assert
        assertNotNull(result);
        assertEquals("Server Outage", result.getTitle());
        assertEquals(TrackingStatus.IN_PROGRESS, result.getStatus());
        verify(ticketRepository).findById(1L);
        verify(ticketRepository).save(ticket);
        verify(auditLogRepository).save(any());
        verify(ticketMapper).toDTO(ticket);
    }

    @Test
    void testUpdateStatus_TicketNotFound() {
        // Arrange
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            ticketService.updateStatus(1L, "IN_PROGRESS");
        });
        assertEquals("Ticket Not Found", exception.getMessage());
        verify(ticketRepository).findById(1L);
        verifyNoMoreInteractions(ticketRepository, ticketMapper);
    }

    @Test
    void testGetUserTickets_Success() {
        // Arrange
        when(authService.getAuthenticatedUser()).thenReturn(authenticatedUser);
        when(ticketRepository.findUserTickets(1L, null, null)).thenReturn(List.of(ticket));
        when(ticketMapper.toDTO(ticket)).thenReturn(ticketResponse);

        // Act
        List<TicketResponseDTO> result = ticketService.getUserTickets(null, null);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Server Outage", result.get(0).getTitle());
        verify(authService).getAuthenticatedUser();
        verify(ticketRepository).findUserTickets(1L, null, null);
        verify(ticketMapper).toDTO(ticket);
    }

    @Test
    void testGetAllTickets_Success() {
        // Arrange
        when(ticketRepository.findFilteredTickets(null, null)).thenReturn(List.of(ticket));
        when(ticketMapper.toDTO(ticket)).thenReturn(ticketResponse);

        // Act
        List<TicketResponseDTO> result = ticketService.getAllTickets(null, null);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Server Outage", result.get(0).getTitle());
        verify(ticketRepository).findFilteredTickets(null, null);
        verify(ticketMapper).toDTO(ticket);
    }

    @Test
    void testGetTicket_Success() {
        // Arrange
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        // Act
        Ticket result = ticketService.geTicket(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Server Outage", result.getTitle());
        verify(ticketRepository).findById(1L);
    }

    @Test
    void testGetTicket_NotFound() {
        // Arrange
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            ticketService.geTicket(1L);
        });
        assertEquals("Ticket with id 1 not found", exception.getMessage());
        verify(ticketRepository).findById(1L);
    }
}