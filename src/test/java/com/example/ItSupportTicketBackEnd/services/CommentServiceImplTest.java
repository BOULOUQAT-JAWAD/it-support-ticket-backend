package com.example.ItSupportTicketBackEnd.services;

import com.example.ItSupportTicketBackEnd.core.enums.Role;
import com.example.ItSupportTicketBackEnd.core.exceptions.DataNotFoundException;
import com.example.ItSupportTicketBackEnd.dtos.requests.CommentRequestDTO;
import com.example.ItSupportTicketBackEnd.entities.Comment;
import com.example.ItSupportTicketBackEnd.entities.Ticket;
import com.example.ItSupportTicketBackEnd.entities.User;
import com.example.ItSupportTicketBackEnd.mappers.CommentMapper;
import com.example.ItSupportTicketBackEnd.repositories.AuditLogRepository;
import com.example.ItSupportTicketBackEnd.repositories.CommentRepository;
import com.example.ItSupportTicketBackEnd.services.implementations.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private TicketService ticketService;

    @Mock
    private AuthService authService;
    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private User authenticatedUser;
    private Ticket ticket;
    private CommentRequestDTO commentRequest;
    private Comment comment;

    @BeforeEach
    void setUp() {

        authenticatedUser = new User();
        authenticatedUser.setId(3L); 
        authenticatedUser.setEmail("it.support1@example.com");
        authenticatedUser.setRole(Role.IT_SUPPORT);

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitle("Server Outage");

        commentRequest = new CommentRequestDTO();
        commentRequest.setContent("Investigating server logs now.");
        commentRequest.setTicketId(1L);

        comment = new Comment();
        comment.setId(1L);
        comment.setContent("Investigating server logs now.");
        comment.setUser(authenticatedUser);
        comment.setTicket(ticket);
    }

    @Test
    void testPersist_Success() {
        // Arrange
        when(authService.getAuthenticatedUser()).thenReturn(authenticatedUser);
        when(ticketService.geTicket(1L)).thenReturn(ticket);
        when(commentMapper.toEntity(commentRequest)).thenReturn(comment);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(auditLogRepository.save(any())).thenReturn(null);

        // Act
        commentService.persist(commentRequest);

        // Assert & verify
        verify(ticketService).geTicket(1L);
        verify(commentMapper).toEntity(commentRequest);
        verify(authService).getAuthenticatedUser();
        verify(commentRepository).save(comment);
        verify(auditLogRepository).save(any());
    }

    @Test
    void testPersist_TicketNotFound() {
        // Arrange
        when(ticketService.geTicket(1L)).thenThrow(new DataNotFoundException("Ticket with id 1 not found"));

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            commentService.persist(commentRequest);
        });
        assertEquals("Ticket with id 1 not found", exception.getMessage());
        verify(ticketService).geTicket(1L);
        verifyNoInteractions(commentRepository);
    }
}