package com.example.ItSupportTicketBackEnd.services.implementations;

import com.example.ItSupportTicketBackEnd.core.enums.TicketActionType;
import com.example.ItSupportTicketBackEnd.dtos.requests.CommentRequestDTO;
import com.example.ItSupportTicketBackEnd.entities.AuditLog;
import com.example.ItSupportTicketBackEnd.entities.Comment;
import com.example.ItSupportTicketBackEnd.entities.Ticket;
import com.example.ItSupportTicketBackEnd.entities.User;
import com.example.ItSupportTicketBackEnd.mappers.CommentMapper;
import com.example.ItSupportTicketBackEnd.repositories.AuditLogRepository;
import com.example.ItSupportTicketBackEnd.repositories.CommentRepository;
import com.example.ItSupportTicketBackEnd.services.AuthService;
import com.example.ItSupportTicketBackEnd.services.CommentService;
import com.example.ItSupportTicketBackEnd.services.TicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TicketService ticketService;
    private final AuthService authService;
    private final AuditLogRepository auditLogRepository;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, TicketService ticketService, AuthService authService, AuditLogRepository auditLogRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.ticketService = ticketService;
        this.authService = authService;
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    @Override
    public void persist(CommentRequestDTO request) {
        Ticket ticket = ticketService.geTicket(request.getTicketId());

        Comment comment = commentMapper.toEntity(request);

        User user = authService.getAuthenticatedUser();
        comment.setUser(user);
        comment.setTicket(ticket);

        Comment newComment = commentRepository.save(comment);

        auditLogRepository.save(
                AuditLog.builder()
                        .ticket(newComment.getTicket())
                        .user(user)
                        .actionType(TicketActionType.COMMENT_ADDED)
                        .details(newComment.getUser().getEmail() + "add comment to ticket with id : "+newComment.getTicket().getId())
                        .build()
        );
    }
}
