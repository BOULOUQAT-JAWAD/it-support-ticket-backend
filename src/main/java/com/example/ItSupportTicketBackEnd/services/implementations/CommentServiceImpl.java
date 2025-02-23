package com.example.ItSupportTicketBackEnd.services.implementations;

import com.example.ItSupportTicketBackEnd.dtos.requests.CommentRequestDTO;
import com.example.ItSupportTicketBackEnd.entities.Comment;
import com.example.ItSupportTicketBackEnd.entities.Ticket;
import com.example.ItSupportTicketBackEnd.mappers.CommentMapper;
import com.example.ItSupportTicketBackEnd.repositories.CommentRepository;
import com.example.ItSupportTicketBackEnd.services.AuthService;
import com.example.ItSupportTicketBackEnd.services.CommentService;
import com.example.ItSupportTicketBackEnd.services.TicketService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TicketService ticketService;
    private final AuthService authService;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, TicketService ticketService, AuthService authService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.ticketService = ticketService;
        this.authService = authService;
    }

    @Override
    public void persist(CommentRequestDTO request) {
        Ticket ticket = ticketService.geTicket(request.getTicketId());

        Comment comment = commentMapper.toEntity(request);

        comment.setUser(
                authService.getAuthenticatedUser()
        );
        comment.setTicket(ticket);

        commentRepository.save(comment);
    }
}
